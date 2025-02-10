/*
 * MIT License
 * 
 * Copyright (c) 2025 Douglas Marcelo Monquero
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, subject to the following conditions:
 * 
 * 1. **Attribution Required**: Any use, modification, or distribution of this
 *    software must include credit to the original author, Douglas Marcelo Monquero,
 *    in any derivative works or publications.
 *
 * 2. **Disclaimer**: THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *    EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *    WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 *    IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.infox.dal;

import java.sql.*;

/**
 * Conexão com o Banco de Dados
 * 
 * @author Douglas Marcelo Monquero
 * @version 1.1
 */

public class ModuloConexao {

	/**
	 * método responsável por estabelecer a conexão com o banco de dados
	 * 
	 * @return conexao
	 */
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
			// a linha abaixo serve de opoio para esclarecer o erro
			// System.out.println(e);
			return null;
		}
	}
}
