using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net;
using System.Net.Sockets;
using System.IO;

/**
 * @Author : JITHINRAJ.P
 * 
 * 
 * */

namespace TCPClient
{
    class TCPClient
    {
        private static TcpClient client;
        static void Main(string[] args)
        {
            client = new TcpClient();

            Console.WriteLine("Connecting to server");


            client.Connect("127.0.0.1", 5150);

            Console.WriteLine("Connected");

            Console.WriteLine("enter The string to be  transmitted");

            String textToTransmit = Console.ReadLine();

            Stream tcpStream = client.GetStream();

            ASCIIEncoding asen = new ASCIIEncoding();
            byte[] ba = asen.GetBytes(textToTransmit);
            Console.WriteLine("Transmitting.....");

            tcpStream.Write(ba, 0, ba.Length);

            byte[] bb = new byte[100];
            int k = tcpStream.Read(bb, 0, 100);

            for (int i = 0; i < k; i++)
                Console.Write(Convert.ToChar(bb[i]));

            client.Close();

        }
    }
}
