package cn.peon.core.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public  class StreamHelp {
	public static StringBuffer getContext(InputStream input,String encode) throws IOException{
		InputStreamReader reader = new InputStreamReader(input, encode);

		String inputString;
		StringBuffer sb = new StringBuffer("");
		
		BufferedReader br = new BufferedReader(reader);
		while ((inputString = br.readLine()) != null) {
			sb.append(inputString + "\r\n");
		}
		br.close();
		reader.close();
		input.close();
		return sb;
	}
	public static void writeTo(String context,OutputStream output,String encode) throws IOException{
		OutputStreamWriter writer = new OutputStreamWriter(output, encode);
		writer.write(context);
		writer.flush();
		writer.close();
		output.close();
	}
	public static void streamCopy(InputStream input,OutputStream output,String encode) throws IOException{
		streamCopy(input, output, true, true,encode);
	}
	public static void streamCopy(InputStream input,OutputStream output,boolean closeinput,boolean closeoutput,String encode) throws IOException{
		
		if (encode!=null&&(!"".equals(""))) {
			InputStreamReader inp = new InputStreamReader(input, encode);
			OutputStreamWriter out = new OutputStreamWriter(output, encode);
			char[] cbuf=new char[1024 * 5];
			while (inp.read(cbuf)!=-1) {
				out.write(cbuf);
			}
			out.flush();
			 if (closeinput) {
				inp.close();
	 	        input.close();
	        }
	        if (closeoutput) {
	        	out.close();
	 	        output.close();
			}
		}
		
		onlyCopyStream(input, output, closeinput, closeoutput);
	       
	       
	}
	private static void onlyCopyStream(InputStream input, OutputStream output,
			boolean closeinput, boolean closeoutput) throws IOException {
		BufferedInputStream inBuff=new BufferedInputStream(input);
	        // 新建文件输出流并对它进行缓冲 
	        BufferedOutputStream outBuff=new BufferedOutputStream(output);
	        // 缓冲数组 
	        byte[] b = new byte[1024 * 5];
	        int len;
	        while ((len =inBuff.read(b)) != -1) {
	            outBuff.write(b, 0, len);
	        }
	        // 刷新此缓冲的输出流 
	        outBuff.flush();
	        //关闭流 
	        if (closeinput) {
	        	inBuff.close();
	 	        input.close();
	        }
	        if (closeoutput) {
	        	outBuff.close();
	 	        output.close();
			}
	}
}
