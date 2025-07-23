import graphics.Model
import graphics.Shader
import graphics.Texture
import org.joml.Vector2f
import org.lwjgl.Version
import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil

class Window {
    // The window handle
    private var window: Long = 0

    fun run() {
        println("Hello LWJGL " + Version.getVersion() + "!")

        init()
        loop()

        // Free the window callbacks and destroy the window
        Callbacks.glfwFreeCallbacks(window)
        GLFW.glfwDestroyWindow(window)

        // Terminate GLFW and free the error callback
        GLFW.glfwTerminate()
        GLFW.glfwSetErrorCallback(null)!!.free()
    }

    private fun init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set()

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        check(GLFW.glfwInit()) { "Unable to initialize GLFW" }

        // Configure GLFW
        GLFW.glfwDefaultWindowHints() // optional, the current window hints are already the default
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE) // the window will stay hidden after creation
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE) // the window will be resizable

        // Create the window
        window = GLFW.glfwCreateWindow(1080, 1080, "Hello World!", MemoryUtil.NULL, MemoryUtil.NULL)
        if (window == MemoryUtil.NULL) throw RuntimeException("Failed to create the GLFW window")

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.

        MemoryStack.stackPush().use { stack ->
            val pWidth = stack.mallocInt(1) // int*
            val pHeight = stack.mallocInt(1) // int*

            // Get the window size passed to glfwCreateWindow
            GLFW.glfwGetWindowSize(window, pWidth, pHeight)

            // Get the resolution of the primary monitor
            val videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor())

            // Center the window
            GLFW.glfwSetWindowPos(window, (videoMode!!.width() - pWidth[0]) / 2, (videoMode.height() - pHeight[0]) / 2)
        }
        // Make the OpenGL context current
        GLFW.glfwMakeContextCurrent(window)
        GLFW.glfwSwapInterval(0)

        // Make the window visible
        GLFW.glfwShowWindow(window)
    }

    private fun loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities() //before textures
        glEnable(GL_TEXTURE_2D)
        //val texture = Texture.get("test.png")
        val vertices: FloatArray = floatArrayOf(
            -0.5f, -0.5f, //0
            -0.5f, 0.5f,  //1
            0.5f, 0.5f,   //2
            0.5f,-0.5f,   //3
        )
        val textureCoordinates = floatArrayOf(
            0f,1f,
            0f,0f,
            1f,0f,
            1f,1f,
        )
        val indices = intArrayOf(
            0,1,2,
            2,3,0
        )
        val model = Model(vertices,textureCoordinates,indices)
        val shader = Shader("test")


        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        var pos = Vector2f(0f,0f)
        while (!GLFW.glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT) // clear the framebuffer
            pos = InputManager.keyInput(window,pos)

            //texture!!.bind()
            shader.bind()
            model.render()






            GLFW.glfwSwapBuffers(window) // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            GLFW.glfwPollEvents()
        }
    }
}
/*            glBegin(GL_QUADS)
           // glColor4f(1f,0f,0f,0f)
            glTexCoord2f(0f,0f)
            glVertex2f(pos.x-0.1f,pos.y+0.1f)
            //glColor4f(0f,1f,0f,0f)
            glTexCoord2f(1f,0f)
            glVertex2f(pos.x+0.1f,pos.y+0.1f)
            //glColor4f(0f,0f,1f,0f)
            glTexCoord2f(1f,1f)
            glVertex2f(pos.x+0.1f,pos.y-0.1f)
           // glColor4f(1f,1f,1f,0f)
            glTexCoord2f(0f,1f)
            glVertex2f(pos.x-0.1f,pos.y-0.1f)
            glEnd()
            texture.bind()
            glBegin(GL_QUADS)
            // glColor4f(1f,0f,0f,0f)
            glTexCoord2f(0f,0f)
            glVertex2f(-pos.x-0.1f,-pos.y+0.1f)
            //glColor4f(0f,1f,0f,0f)
            glTexCoord2f(1f,0f)
            glVertex2f(-pos.x+0.1f,-pos.y+0.1f)
            //glColor4f(0f,0f,1f,0f)
            glTexCoord2f(1f,1f)
            glVertex2f(-pos.x+0.1f,-pos.y-0.1f)
            // glColor4f(1f,1f,1f,0f)
            glTexCoord2f(0f,1f)
            glVertex2f(-pos.x-0.1f,-pos.y-0.1f)
            glEnd()

 */