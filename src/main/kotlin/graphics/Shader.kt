package graphics

import org.lwjgl.opengl.GL20.*
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import kotlin.properties.Delegates
import kotlin.system.exitProcess

class Shader(private val filename:String) {
    private var program = glCreateProgram()
    private var vertexShader = glCreateShader(GL_VERTEX_SHADER)
    private var fragmentShader = glCreateShader(GL_FRAGMENT_SHADER)
    companion object{
        const val VERTICES_ATTRIBUTE = "vertices"
    }
    init {
        createVertexShader(vertexShader)
        createFragmentShader(fragmentShader)
        attachShaders()

        validate()
    }
    fun bind(){
        glUseProgram(program)
    }
    private fun validate(){
        glLinkProgram(program)
        if(glGetProgrami(program, GL_LINK_STATUS)!=1) {
            System.err.println(glGetProgramInfoLog(program))
            exitProcess(-1)
        }
        glValidateProgram(program)
        if(glGetProgrami(program, GL_VALIDATE_STATUS)!=1) {
            System.err.println(glGetProgramInfoLog(program))
            exitProcess(-1)
        }
    }
    private fun attachShaders(){
        glAttachShader(program,vertexShader)
        glAttachShader(program,fragmentShader)
    }
    private fun createVertexShader(shaderId:Int){
        glShaderSource(vertexShader,readFile("$filename.vert"))
        glCompileShader(vertexShader)
        if(glGetShaderi(vertexShader, GL_COMPILE_STATUS )!=1){
            System.err.println(glGetShaderInfoLog(vertexShader))
        }
    }
    private fun createFragmentShader(shaderId:Int){
        glShaderSource(vertexShader,readFile("$filename.frag"))
        glCompileShader(vertexShader)
        if(glGetShaderi(vertexShader, GL_COMPILE_STATUS )!=1){
            System.err.println(glGetShaderInfoLog(vertexShader))
        }
    }
    private fun readFile(filename: String):String{
        val str = StringBuilder()
        try {
            val reader = BufferedReader(FileReader(File("src/main/resources/shaders/$filename")))
            var line: String?
            while( reader.readLine().also { line= it } !=null){
                str.append(line)
                str.append("\n")
            }
            reader.close()
        }catch (e:Exception){
            e.printStackTrace()
        }
        return str.toString()
    }

}