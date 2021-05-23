CIS 457 Final Project - Procedural Terrain
Sean Surtz

![](https://i.imgur.com/pxMeI5k.mp4)

Description
	This program will procedurally generate 3D terrain based on player position

Note: 	
  This project probably WILL NOT WORK with Java SDK 16 - it gets an error with tools.jar.
	This has been tested using Java SDK JDK 15.
	Make sure you have JDK 15 installed. (14 may work as well)
	IntelliJ will likely already have multiple java SDKs.  To check, open IntelliJ and press FILE -> Project Structure and then under the Project Settings tab press Project and then click the Project SDK dropdown

To Run the Program:
	1. Open IntelliJ
	2. Click the file button in the top left corner of Intellij
	3. Click Open
	4. In the open file or project dialog, locate the unzipped CIS-457-Project Folder and click on it
	5. Press OK
	6. Go to File -> project structure
	7. Under the Project tab set Project SDK to version 15 - IT MOST LIKELY WILL NOT WORK WITH 16.
	8. Under the modules tab set the Module SDK to version 15 - IT MOST LIKELY WILL NOT WORK WITH 16.
		8.1 In case it does not update, Press file again -> invalidate caches/restart
	9. You may have to build the project by pressing ctrl + f9 or by clicking build in the top bar of intellij and then build project
	10. building may take a litle bit
	11. After building, it may indicate syntax errors in the editor; it should still be able to run.  This seems to be an issue with IntelliJ importing a project like this.
		11.1. To fix that, you can press file then go down to Invalidate Caches/Restart
	12. Then go to src -> Game -> RunGame.java, press the green arrow next to the main method
	13. the program should run

Controls:
	Movement Controls:  
	forward = W,
	backward = S,
	right = D,
	left = A
	Up = SPACE or BACKSPACE,
	Down = LEFT SHIFT

	Rotate camera by moving mouse
	
	as you move, more terrain will generate and terrains that are further away will be removed

Resources:
	LightWeight Java Game Library 3
	PNGDecoder
	Java OpenGL Math Library
	Fast Noise Lite
	
	Antonio Hernandez Bejarano, 3D Game Development with LWJGL 3
		https://lwjglgamedev.gitbooks.io/3d-game-development-with-lwjgl/content/ 
	Thin Matrix LWJGL 2 tutorials
		https://www.youtube.com/watch?v=VS8wlS9hF8E&list=PLRIWtICgwaX0u7Rf9zkZhLoLuZVfUksDP
	Joey de Vries LearnOpenGL
		 https://learnopengl.com/
	Open-GL Tutorial
		http://www.opengl-tutorial.org/
	MC Noise Generation settings
		https://minecraft.fandom.com/wiki/Noise_generator
