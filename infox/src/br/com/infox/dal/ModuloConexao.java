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
import br.com.infox.utils.EnvLoader;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Conexão com o Banco de Dados usando SSL
 * 
 * @author Douglas Marcelo Monquero
 * @version 1.3
 */

public class ModuloConexao {

    public static Connection conector() {
        Connection conexao = null;
        String driver = "com.mysql.cj.jdbc.Driver";

        try {
            // Carregar o arquivo 'ca.pem' do classpath (pasta resources)
            InputStream caInputStream = ModuloConexao.class.getClassLoader().getResourceAsStream("ca.pem");

            if (caInputStream == null) {
                throw new RuntimeException("Arquivo 'ca.pem' não encontrado no classpath.");
            }

            // Criar um arquivo temporário para o MySQL Connector usar
            String tempFilePath = System.getProperty("java.io.tmpdir") + "/ca.pem";
            Files.copy(caInputStream, Paths.get(tempFilePath), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

            // Configuração da URL com SSL
            String url = "jdbc:mysql://monquero.sytes.net:3306/dbinfox?characterEncoding=utf-8" +
                         "&useSSL=true&requireSSL=true" +
                         "&verifyServerCertificate=false" +    // Ignora a verificação do CN
                         "&serverSslCert=" + tempFilePath;     // Define o caminho temporário do CA

            // Usuário e senha do MySQL
            String user = "dba";
            String password = "Debase33@dba";

            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            //System.out.println("Conectado ao banco com sucesso usando SSL!");

            return conexao;

        } catch (ClassNotFoundException | SQLException | java.io.IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
