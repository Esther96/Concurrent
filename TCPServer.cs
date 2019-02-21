using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net;
using System.Net.Sockets;

/**
 * @Author : ESTERA
 * 
 * 
 * */
namespace TCPServer
{
    class TCPServer
    {
        static void Main(string[] args)
        {
            try
            {
                IPAddress ipAd = IPAddress.Parse("127.0.0.1");

                TcpListener myList = new TcpListener(ipAd, 5150);

                myList.Start();

                Console.WriteLine("The server is running at port 8001...");
                Console.WriteLine("The local End point is  :" +
                                  myList.LocalEndpoint);
                Console.WriteLine("Waiting for a connection.....");

                while (true)
                {
                    Socket s = myList.AcceptSocket();

                    Console.WriteLine("Connection accepted from " + s.RemoteEndPoint);

                    byte[] b = new byte[100];
                    while (true)
                    {
                        int k = s.Receive(b);
                        Console.WriteLine("Recieved...");
                        for (int i = 0; i < k; i++)
                            Console.Write(Convert.ToChar(b[i]));

                        ASCIIEncoding asen = new ASCIIEncoding();
                        s.Send(asen.GetBytes("The string was recieved by the server."));
                        Console.WriteLine("\nSent Acknowledgement");
                        //s.Close();

                        if (k == 0)
                        {
                            s.Close();
                            break;
                        }
                    }
                }
                myList.Stop();
                Console.ReadLine();
            }
            catch (Exception e)
            {

            }
        }
    }
}
