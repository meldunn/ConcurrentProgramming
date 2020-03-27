import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

/*
 *  to ask TA
 *  1. min dimension of rectangle allowed, 1x1, 2x2, 3x3?
 *  2. clarify overlapping, is it just the rectangles in the current threads or ALL rectangles drawn 
 *  
 *  
 *  
 *  TODO
 *  - rearrange code so i can have the least amount of lines in a synchronized method
 *  - implement run
 *  - create the threads and run them
 *  
 */


import java.awt.Color;
import java.util.ArrayList;

public class q1 {

    // Parameters
    public static int n;
    public static int width;
    public static int height;
    public static int k;
    public static BufferedImage outputimage;
    
   

  //create list to store all the threads
    static ArrayList<myThread> listThreads = new ArrayList<myThread>();
    
    public static void main(String[] args) {
        try {


        	
        	if (args.length != 4) {
        		System.out.println("Wrong number of command line arguments. Program will terminate. You inputed " + args.length + " arguments.");
        		System.exit(1);
        	}
        	width = Integer.parseInt(args[0]); // width of image
        	height = Integer.parseInt(args[1]); // height of image
        	n = Integer.parseInt(args[2]); // number of threads
        	k = Integer.parseInt(args[3]); // number of rectangles to draw
        	

            // once we know what size we want we can creat an empty image
            //BufferedImage outputimage = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
            outputimage = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
           
         
          
            
            //make array of n elements of ints to store how many rectangles each thread is responsible for
            int[] numRecList = new int[n];
            int quotient = k/n;
            int remainder = k%n;
            
            for (int j = 0; j < n; j++) {
            	numRecList[j] = quotient;
            	if (j < remainder) {
            		numRecList[j]++;
            	}
            	
            }
            
        
            
            long startTime = System.currentTimeMillis();
            
            //create n threads and start them 
            for (int i = 0; i < n; i++) {
            	myThread temp = new myThread(numRecList[i], i);
            	listThreads.add(temp);
            	temp.start();
            }
            
            for (Thread t : listThreads) {
            	t.join(); 
            }
            
            long endTime = System.currentTimeMillis();
            
            long totalTime = endTime - startTime;
            
            System.out.println("Time taken for " + n + " thread(s) to draw " + k +  " rectangles is " + totalTime);
            		
            
            
            // The easiest mechanisms for getting and setting pixels are the
            // BufferedImage.setRGB(x,y,value) and getRGB(x,y) functions.
            // Note that setRGB is synchronized (on the BufferedImage object).
            // Consult the javadocs for other methods.

            // The getRGB/setRGB functions return/expect the pixel value in ARGB format, one byte per channel.  For example,
            //  int p = img.getRGB(x,y);
            // With the 32-bit pixel value you can extract individual colour channels by shifting and masking:
            //  int red = ((p>>16)&0xff);
            //  int green = ((p>>8)&0xff);
            //  int blue = (p&0xff);
            // If you want the alpha channel value it's stored in the uppermost 8 bits of the 32-bit pixel value
            //  int alpha = ((p>>24)&0xff);
            // Note that an alpha of 0 is transparent, and an alpha of 0xff is fully opaque.
            
            // ------------------------------------
            
//            for (int num = 0; num < k; num++) {
//            	//create rectangle
//            	int[] crRecDimension = getRecDimension();	
//            	createRectangle(crRecDimension[0],crRecDimension[1], outputimage);
//            }
            // Write out the image
            File outputfile = new File("outputimage.png");
            ImageIO.write(outputimage, "png", outputfile);

        } catch (Exception e) {
            System.out.println("ERROR " +e);
            e.printStackTrace();
        }
    }

    
    public static void drawRectangle(Rectangle recToDraw) {
    	Color fillColor = getRandomFillColor(); // get the color to fill this rectangle
    	
    	int w = recToDraw.rWidth;
    	int h = recToDraw.rHeight;
    	int[] startingPoint = new int[2];
    	startingPoint[0] = recToDraw.rStartX;
    	startingPoint[1] = recToDraw.rStartY;
    	
    			
    	for (int xaxis = 0; xaxis < w; xaxis++) {
    		for (int yaxis = 0; yaxis < h; yaxis++) {
    			if (yaxis == 0) {
    				outputimage.setRGB(startingPoint[0]+xaxis, startingPoint[1],new Color(0,0,0,255).getRGB());//draw top border
    			}
    			else if (yaxis == h - 1) {
    				outputimage.setRGB(startingPoint[0]+xaxis, startingPoint[1]+yaxis ,new Color(0,0,0,255).getRGB());//draw bottom border
    			}
    			else if (xaxis == 0 || xaxis == w -1) {
    				outputimage.setRGB(startingPoint[0]+xaxis, startingPoint[1]+yaxis ,new Color(0,0,0,255).getRGB()); // draw edge
    			}
    			else {
    				outputimage.setRGB(startingPoint[0]+xaxis, startingPoint[1]+yaxis ,fillColor.getRGB()); //fill the rectangle
    			}
    				
    		}
    	}
    }
    
    public static Color getRandomFillColor() {
    	int r = (int) (Math.random() * 255); //get number between 0 and 255 incl
    	int g = (int) (Math.random() * 255);
    	int b = (int) (Math.random() * 255);
    	//System.out.println("r: " + r + " g: " + g + " b: " + b);
    	return new Color(r,g,b,255);
    }

	public static int[] getStartingLoc(int recWidth, int recHeight) {
		
			int x = (int) (Math.random() * (width - recWidth)); //ensures rectangle will fit onto image
			int y = (int) (Math.random() * (height - recHeight)); //ensures rectangle will fit onto image

			int[] start = { x, y };
			return start;

		
	}
	public static int[] getRecDimension() {
		int x = (int) (Math.random() * (width - 3)) + 3; //assume that min dim is 3x3 for now
		int y = (int) (Math.random() * (height -3)) + 3;
		int[] dim = {x,y};
		
		//int[] dim = {100,200};
		return dim;
		
		
		
		
	}
	
	//return true if  overlap
    synchronized public static boolean isOverlap(int startX, int startY, int endX, int endY) {
    	
    	for (myThread t : listThreads) {
    		
    		int numTrueConditions = 0;
    		
    		if (t.recInProgress != null) {
    			//check if y coordinates dont overlap ie rectangles wont coincide
        		if (endY < t.recInProgress.rStartY || startY > (t.recInProgress.rStartY + t.recInProgress.rHeight) ) {
        			numTrueConditions++;
        		}
        		//check if x coordinates dont overlap ie rectangles wont coincide
        		if (endX < t.recInProgress.rStartX || startX > (t.recInProgress.rStartX + t.recInProgress.rWidth)) {
        			numTrueConditions++;
        		}
        		if (numTrueConditions == 0) {
        		
        			return true; //overlapping
        		}
    		}   		
    	}    	
    	return false;
    }
    
    synchronized public static void createRecObj(int rWidth, int rHeight, int rStartX,	int rStartY, int threadID) {
    	Rectangle rec = new Rectangle(rWidth, rHeight, rStartX, rStartY);
    	//******make sure the id and index are the same, otherwise will have to loop thru and compare id
    	
    	listThreads.get(threadID).recInProgress = rec;
    		  	
    }
}
