package br.com.infox.dal;

import java.sql.*;

public class ModuloConexao {

	// metodo responsavel por estabelecer a conexao com o banco
	public static Connection conector() {
		Connection conexao = null;
		// a linha abaixo "chama" o driver que eu importei para bibliotecas
		String driver = "com.mysql.cj.jdbc.Driver";
		// Armazenando informaçoes referente ao banco
		String url = "jdbc:mysql://localhost:3306/dbinfox?characterEncoding=utf-8";
		String user = "dba";
		String password = "Debase33@";

		// Estabelecendo a conexao com o banco
		try {
			Class.forName(driver);
			conexao = DriverManager.getConnection(url, user, password);
			return conexao;
		} catch (Exception e) {
			//a linha abaixo serve de opoio para esclarecer o erro
			//System.out.println(e);
			return null;
		}
	}
}
