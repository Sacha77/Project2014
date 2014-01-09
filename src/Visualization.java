import java.util.ArrayList;

import org.lwjgl.opengl.Display;		// Import LWJGL Display
import org.lwjgl.opengl.DisplayMode;	// Import LWJGL DisplayMode
import org.lwjgl.opengl.GL11;			// Import LWJGL GL11
import org.lwjgl.util.glu.GLU;
import org.lwjgl.input.Keyboard;		// Import LWJGL Input

import javax.swing.JOptionPane; 		// Import Swing JOptionPane

public class Visualization
{
	private DisplayMode dm;				// The Display Mode
    private boolean fullscreen=true;	// Fullscreen Flag Set To Fullscreen Mode By Default
    private static ArrayList<Integer> line = new ArrayList<Integer>();
    private static ArrayList<Integer> level = new ArrayList<Integer>();
    private static ArrayList<double[]> items = new ArrayList<double[]>();
    private int cntr = 0;
   

	float rotquad;
	
	
	public void addItem(double[] dim, int line, int level){
		items.add(dim);
		this.line.add(line);
		this.level.add(level);
		
	}

    private void ReSizeGLScene(int width, int height)			// Resize And Initialize The GL Window
    {
    	if (height==0)											// Prevent A Divide By Zero By
    	{
    		height=1;											// Making Height Equal One
    	}

    	GL11.glViewport(0,0,width,height);						// Reset The Current Viewport

    	GL11.glMatrixMode(GL11.GL_PROJECTION);					// Select The Projection Matrix
    	GL11.glLoadIdentity();									// Reset The Projection Matrix

        // Calculate The Aspect Ratio Of The Window
        GLU.gluPerspective(45.0f, (float)width/(float)height, 0.1f, 300.0f);
        
    	GL11.glMatrixMode(GL11.GL_MODELVIEW);					// Select The Modelview Matrix
    	GL11.glLoadIdentity();									// Reset The Modelview Matrix
    }




    
    //init
    private boolean InitGL() 									// All Setup For OpenGL Goes Here
    {
        GL11.glShadeModel(GL11.GL_SMOOTH);						// Enable Smooth Shading
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);				// Black Background
        GL11.glClearDepth(1.0f);								// Depth Buffer Setup
        GL11.glEnable(GL11.GL_DEPTH_TEST);						// Enables Depth Testing
        GL11.glDepthFunc(GL11.GL_LEQUAL);						// The Type Of Depth Testing To Do
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);	// Really Nice Perspective Calculations
        return true;
    }

    

    //Render
    private boolean DrawGLScene()								// Here's Where We Do All The Drawing
    {
    	GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);	// Clear Screen And Depth Buffer
    	GL11.glLoadIdentity();												// Reset The Current Modelview Matrix

    	GL11.glTranslatef(0.0f, 0.0f,-100.0f);					// Translate Into The Screen __ Units
		GL11.glRotatef(rotquad,0.0f,1.0f,0.0f);					// Rotate The cube around the Y axis
		GL11.glRotatef(100.0f,10.0f,0.0f,10.0f);
    	
    	for(int i=0; i<items.size(); i++){
    		double[] dimPrev={0,0,0};
    		if(i!=0)
    			dimPrev = items.get(i-1);

    		float xP = (float) dimPrev[0];
	    	float yP = (float) dimPrev[1];
	    	float zP = (float) dimPrev[2];
	
    		double[] dimCurr = items.get(i);
	    	float xC = (float) dimCurr[0];
	    	float yC = (float) dimCurr[1];
	    	float zC = (float) dimCurr[2];
	    	
	    	float lineLength = 0;
	    	for(int j=0; j<i; j++){
	    		lineLength += items.get(j)[0] + 0.1f;
	    	}	    	
	    	
	    	int inLine = (int)(GreedyFitness.container[0]/xC);
	    	

	    	if(i!=0 && line.get(i) != line.get(i-1)){
	    		int lines = line.get(i-1)-line.get(i);
    			GL11.glTranslatef( 0.0f, lines*(yP+yC+0.1f), 0.0f);
    			GL11.glTranslatef( -inLine*(xP+xC+0.1f), 0.0f, 0.0f);
	    	}
	    	
	    	if(i!=0 && level.get(i) != level.get(i-1)){
	    		GL11.glTranslatef( 0.0f, 0.0f, zP+zC+0.1f);   		
	    	}
	    	
			
	    	GL11.glTranslatef( (xP+xC+0.1f), 0.0f,0.0f);
	    	
			GL11.glBegin(GL11.GL_QUADS);							// Draw a cube with quads
				GL11.glColor3f(0.0f,1.0f,0.0f);						// Color Blue
				GL11.glVertex3d( xC, yC,-zC);					// Top Right Of The Quad (Top)
				GL11.glVertex3d(-xC, yC,-zC);					// Top Left Of The Quad (Top)
				GL11.glVertex3d(-xC, yC, zC);					// Bottom Left Of The Quad (Top)
				GL11.glVertex3d( xC, yC, zC);					// Bottom Right Of The Quad (Top)
				
				GL11.glColor3f(1.0f,0.5f,0.0f);						// Color Orange
				GL11.glVertex3d( xC,-yC, zC);					// Top Right Of The Quad (Bottom)
				GL11.glVertex3d(-xC,-yC, zC);					// Top Left Of The Quad (Bottom)
				GL11.glVertex3d(-xC,-yC,-zC);					// Bottom Left Of The Quad (Bottom)
				GL11.glVertex3d( xC,-yC,-zC);					// Bottom Right Of The Quad (Bottom)
				
				GL11.glColor3f(1.0f,0.0f,0.0f);						// Color Red
				GL11.glVertex3d( xC, yC, zC);					// Top Right Of The Quad (Front)
				GL11.glVertex3d(-xC, yC, zC);					// Top Left Of The Quad (Front)
				GL11.glVertex3d(-xC,-yC, zC);					// Bottom Left Of The Quad (Front)
				GL11.glVertex3d( xC,-yC, zC);					// Bottom Right Of The Quad (Front)
				
				GL11.glColor3f(1.0f,1.0f,0.0f);						// Color yCellow
				GL11.glVertex3d( xC,-yC,-zC);					// Top Right Of The Quad (Back)
				GL11.glVertex3d(-xC,-yC,-zC);					// Top Left Of The Quad (Back)
				GL11.glVertex3d(-xC, yC,-zC);					// Bottom Left Of The Quad (Back)
				GL11.glVertex3d( xC, yC,-zC);					// Bottom Right Of The Quad (Back)
				
				GL11.glColor3f(0.0f,0.0f,1.0f);						// Color Blue
				GL11.glVertex3d(-xC, yC, zC);					// Top Right Of The Quad (Left)
				GL11.glVertex3d(-xC, yC,-zC);					// Top Left Of The Quad (Left)
				GL11.glVertex3d(-xC,-yC,-zC);					// Bottom Left Of The Quad (Left)
				GL11.glVertex3d(-xC,-yC, zC);					// Bottom Right Of The Quad (Left)
				
				GL11.glColor3f(1.0f,0.0f,1.0f);						// Color Violet
				GL11.glVertex3d( xC, yC,-zC);					// Top Right Of The Quad (Right)
				GL11.glVertex3d( xC, yC, zC);					// Top Left Of The Quad (Right)
				GL11.glVertex3d( xC,-yC, zC);					// Bottom Left Of The Quad (Right)
				GL11.glVertex3d( xC,-yC,-zC);					// Bottom Right Of The Quad (Right)
			GL11.glEnd();
    	}
		
		
		rotquad +=0.1f;

		
		
        return true;
    }


    
    //kill
    private void KillGLWindow()									// Kill The Window
    {
        Display.destroy();
    }

    /*	This Code Creates Our OpenGL Window.  Parameters Are:					*
     *	title			- Title To Appear At The Top Of The Window				*
     *	width			- Width Of The GL Window Or Fullscreen Mode				*
     *	height			- Height Of The GL Window Or Fullscreen Mode			*
     *	bits			- Number Of Bits To Use For Color (8/16/24/32)			*
     *	fullscreenflag	- Use Fullscreen Mode (TRUE) Or Windowed Mode (FALSE)	*/



    //create
    private boolean CreateGLWindow(String title, int width, int height, int bits, boolean fullscreenflag) throws Exception
    {
        DisplayMode d[] = Display.getAvailableDisplayModes();
        for (int i = 0; i < d.length; i++)
        {
            if (d[i].getWidth() == width && d[i].getHeight() == height && d[i].getBitsPerPixel() == bits)
            {
                dm = d[i];
                break;
            }
        }
        Display.setDisplayMode(dm);								// Set The Display Mode
        Display.setFullscreen(fullscreenflag);					// Set Fullscreen Flag
        Display.setTitle(title);								// Add A Title To The Window
        Display.create();										// Create The LWJGL Display
        ReSizeGLScene(width, height);							// Set Up Our Perspective GL Screen

        if (!InitGL())											// Initialize Our Newly Created GL Window
    	{
    		KillGLWindow();										// Reset The Display
    		JOptionPane.showConfirmDialog(null, "Initialization Failed.", "ERROR", JOptionPane.OK_OPTION );
    		return false;										// Return FALSE
    	}

    	return true;
    }

    
    
    public void start(){
    	Visualization MainObject = new Visualization();
    	MainObject.MainLoop();
    }
  
    public boolean MainLoop()									
    {
    	boolean done=false;

     	// Ask The User Which Screen Mode They Prefer
     	if (JOptionPane.showConfirmDialog(null, "Would You Like To Run In Fullscreen Mode?",
     	"Start Fullscreen?", JOptionPane.YES_NO_OPTION) == 1)
     	{
     		fullscreen = false;									// Windowed Mode
     	}
        try
        {
        	 // Create Our OpenGL Window
			 if (!CreateGLWindow("Apron Tutorials", 640, 480, 16, fullscreen))
			 {
			 		return false;								// Quit If Window Was Not Created
			 }

             while(!done)										// Loop That Runs While done=false
             {
      			// Draw The Scene.  Watch For ESC Key And Quit Messages From DrawGLScene()
          		if ((!DrawGLScene()) || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) || Display.isCloseRequested())	// Was There A Quit Received?
      			{
      				done=true;
      			}
         		else
         		{
         			Display.update();							// Update Display
         		}

         		if (Keyboard.isKeyDown(Keyboard.KEY_F1))		// Is F1 Being Pressed?
         		{
         			fullscreen = !fullscreen;					// Toggle Fullscreen / Windowed Mode
         	        try { Display.setFullscreen(fullscreen); }
         	        catch(Exception e) { e.printStackTrace(); }
         		}
             }
             // Shutdown
             KillGLWindow();									// Kill The Window
        }
        catch (Exception e)
        {
        	KillGLWindow();										// Kill The Window
        	e.printStackTrace();
        	System.exit(0);										// Exit The Program
        }

    	return true;
    }
}