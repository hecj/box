package cn.codegen.jfinal.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class ALL {
	public static void main(String[] args) throws FileNotFoundException, IOException, SQLException, Exception {
		DUBBO_MYSQL_JFINALX.run();
		DUBBO_JFINALX_BUILD.run();
	}
}
