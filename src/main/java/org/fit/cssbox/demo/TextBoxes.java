/**
 * TextBoxes.java
 * Copyright (c) 2005-2007 Radek Burget
 *
 * CSSBox is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 * CSSBox is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *  
 * You should have received a copy of the GNU Lesser General Public License
 * along with CSSBox. If not, see <http://www.gnu.org/licenses/>.
 *
 * Created on 13.11.2007, 14:34:04 by burgetr
 */
package org.fit.cssbox.demo;

import org.fit.cssbox.css.CSSNorm;
import org.fit.cssbox.css.DOMAnalyzer;
import org.fit.cssbox.io.DOMSource;
import org.fit.cssbox.io.DefaultDOMSource;
import org.fit.cssbox.io.DefaultDocumentDataSource;
import org.fit.cssbox.io.DocumentDataSource;
import org.fit.cssbox.layout.Box;
import org.fit.cssbox.layout.BrowserCanvas;
import org.fit.cssbox.layout.ElementBox;
import org.fit.cssbox.layout.TextBox;
import org.w3c.dom.Document;

import java.net.URL;


/**
 * This demo shows how the rendered box tree can be accessed. It renders the document 
 * and prints the list of text boxes together with their positions on the page.
 * 
 * @author burgetr
 */
public class TextBoxes
{

    /**
     * Recursively prints the text boxes from the specified tree
     */
    private static void printTextBoxes(Box root)
    {
        if (root instanceof TextBox)
        {
            //text boxes are just printed
            TextBox text = (TextBox) root;
            System.out.println("x=" + text.getAbsoluteBounds().x + " y=" + text.getAbsoluteBounds().y + " text=" + text.getText());
        }
        else if (root instanceof ElementBox)
        {
            //element boxes must be just traversed
            ElementBox el = (ElementBox) root;
            for (int i = el.getStartChild(); i < el.getEndChild(); i++)
                printTextBoxes(el.getSubBox(i));
        }
    }
    
    /**
     * main method
     */
    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.err.println("Usage: TextBoxes <url>");
            System.exit(0);
        }
        
        try {
            URL url = new URL(args[0]);
            //Open the network connection 
            DocumentDataSource docSource = new DefaultDocumentDataSource();
            
            //Parse the input document
            DOMSource parser = new DefaultDOMSource(docSource);
            Document doc = parser.parse(url);
            
            //Create the CSS analyzer
            DOMAnalyzer da = new DOMAnalyzer(doc, url);
            da.attributesToStyles(); //convert the HTML presentation attributes to inline styles
            da.addStyleSheet(null, CSSNorm.stdStyleSheet(), DOMAnalyzer.Origin.AGENT); //use the standard style sheet
            da.addStyleSheet(null, CSSNorm.userStyleSheet(), DOMAnalyzer.Origin.AGENT); //use the additional style sheet
            da.getStyleSheets(); //load the author style sheets
            
            //Create the browser canvas
            BrowserCanvas browser = new BrowserCanvas(da.getRoot(), da, url);
            //Disable the image loading
            browser.getConfig().setLoadImages(false);
            browser.getConfig().setLoadBackgroundImages(false);
            
            //Create the layout for 1000x600 pixels
            browser.createLayout(new java.awt.Dimension(1000, 600));
            
            //Display the result
            printTextBoxes(browser.getViewport());
            
            docSource.close();
            
        } catch (Exception e) {
            System.err.println("Error: "+e.getMessage());
            e.printStackTrace();
        }

    }
    
}
