import org.joml.Vector2f
import org.joml.Vector4f
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.GLFW_TRUE
import org.lwjgl.glfw.GLFW.glfwGetKey

class InputManager() {
    companion object{

        fun keyInput(window:Long,pos:Vector2f): Vector2f {

            fun isPressed(key:Int,run:()->(Unit)) {
                if(glfwGetKey(window,key) == GLFW_TRUE) run()
            }

            isPressed(GLFW.GLFW_KEY_ESCAPE){ GLFW.glfwSetWindowShouldClose(window, true)}
            isPressed(GLFW.GLFW_KEY_A){ pos.x+=-0.001f }
            isPressed(GLFW.GLFW_KEY_W){ pos.y+=0.001f }
            isPressed(GLFW.GLFW_KEY_S){ pos.y+=-0.001f }
            isPressed(GLFW.GLFW_KEY_D){ pos.x+=0.001f }


            if(pos.x>0.9f) pos.x=0.9f
            if(pos.y>0.5f) pos.y=0.5f
            if(pos.x<-0.5f) pos.x=-0.5f
            if(pos.y<-0.5f) pos.y=-0.5f
            return pos
        }
    }

}