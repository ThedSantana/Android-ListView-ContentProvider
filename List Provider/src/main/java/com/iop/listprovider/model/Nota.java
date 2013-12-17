package com.iop.listprovider.model;

/**
 * Created by Rafael Iop
 *
 * Contém como atributos o nome das colunas da tabela "nota" do banco de dados
 *
 */

public class Nota {

    // Nome da tabela no bd
    public static final String TABELA = "nota";

    // Atributos básicos
    public static String ID = "_id";
    public static String DATA = "data";

    // Prioridade e tipos (as cores estão em /values/colors.xml)
    public static String PRIORIDADE = "prioridade";
    public static int PRIORIDADE_BAIXA = 0;
    public static int PRIORIDADE_MEDIA = 1;
    public static int PRIORIDADE_ALTA = 2;

    // Informações da Nota
    public static String TITULO = "titulo";
    public static String DESCRICAO = "descricao";

    // Usada para obter apenas o começo da string descrição
    // SUBSTR: Obtêm apenas uma parte da string (de 0 até 21)
    // REPLACE: Substitui as quebras de linha (\n) por espaços
    public static int DESCRICAO_RESUMIDA_TAM = 21;
    public static String DESCRICAO_RESUMIDA = "REPLACE(SUBSTR(descricao, 0, " +
            DESCRICAO_RESUMIDA_TAM+"),'\n',' ') as descricao";

}
