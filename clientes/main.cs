using System;
using System.IO;
using System.Net;
using System.Text;
using System.Runtime.Serialization.Json;

public class main
{
    static public void Main ()
    {
        string url = "http://192.168.1.39:3001/rrhh/trabajadores";

        WebRequest request = WebRequest.Create (url);
        //Obtener la respuesta de la peticion
        WebResponse response = request.GetResponse();

        Console.WriteLine (((HttpWebResponse)response).StatusDescription);
        //Obtener el stream que contiene el contenido retonado por el servidor
        Stream dataStream = response.GetResponseStream ();
        // LLevar el stream a Stream Reader para su lectura mas facil
        StreamReader reader = new StreamReader (dataStream);
        //Leer el contenido
        string json = reader.ReadToEnd();

        //Imprimir la respuesta
        Console.WriteLine (json);
        JsonObject trabajadores = (JsonObject)JsonObject.Load(json);
        foreach (JsonObject trabajador in trabajadores)
		{
		   Console.WriteLine(trabajador["nombre"]);
		}


        //Finalmente limpiar los streams y la respuesta
        reader.Close ();
        response.Close ();
    }
}