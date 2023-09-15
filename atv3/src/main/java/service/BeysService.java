package service;

import java.util.Scanner;

import java.time.LocalDate;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import beys_dao.DAO;
import beys_dao.beyDAO;
import model.Beys;
import spark.Request;
import spark.Response;


public class BeysService {

	private beyDAO beyDAO = new beyDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_DESCRICAO = 2;
	private final int FORM_ORDERBY_PRECO = 3;
	private final int FORM_ORDERBY_NOME = 4;
	private final int FORM_ORDERBY_TIPO = 5;
	
	
	public BeysService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Beys(), FORM_ORDERBY_DESCRICAO);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Beys(), orderBy);
	}

	
	public void makeForm(int tipo, Beys beyblade, int orderBy) {
		String nomeArquivo = "form.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umBeys = "";
		if(tipo != FORM_INSERT) {
			umBeys += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umBeys += "\t\t<tr>";
			umBeys += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/beyblade/list/1\">Novo Beyblade</a></b></font></td>";
			umBeys += "\t\t</tr>";
			umBeys += "\t</table>";
			umBeys += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/produto/";
			String name, descricao, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Beys";
				descricao = "Informações gerais";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + beyblade.getID();
				name = "Atualizar Beyblade (ID " + beyblade.getID() + ")";
				descricao = beyblade.getDescricao();
				buttonLabel = "Atualizar";
			}
			umBeys += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umBeys += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umBeys += "\t\t<tr>";
			umBeys += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umBeys += "\t\t</tr>";
			umBeys += "\t\t<tr>";
			umBeys += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umBeys += "\t\t</tr>";
			umBeys += "\t\t<tr>";
			umBeys += "\t\t\t<td>Nome: <input class=\"input--register\" type=\"text\" name=\"nome\" value=\""+ beyblade.getNome() +"\"></td>";
			umBeys += "\t\t\t<td>Tipo: <input class=\"input--register\" type=\"text\" name=\"tipo\" value=\""+ beyblade.getTipo() +"\"></td>";
			umBeys += "\t\t\t<td>&nbsp;Descrição: <input class=\"input--register\" type=\"text\" name=\"descricao\" value=\""+ descricao +"\"></td>";
			umBeys += "\t\t\t<td>Preco: <input class=\"input--register\" type=\"text\" name=\"preco\" value=\""+ beyblade.getPreco() +"\"></td>";
			umBeys += "\t\t</tr>";
			umBeys += "\t\t<tr>";
			umBeys += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umBeys += "\t\t</tr>";
			umBeys += "\t</table>";
			umBeys += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umBeys += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umBeys += "\t\t<tr>";
			umBeys += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Beyblade (ID " + beyblade.getID() + ")</b></font></td>";
			umBeys += "\t\t</tr>";
			umBeys += "\t\t<tr>";
			umBeys += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umBeys += "\t\t</tr>";
			umBeys += "\t\t<tr>";
			umBeys += "\t\t\t<td>Nome: "+ beyblade.getNome() +"</td>";
			umBeys += "\t\t\t<td>Tipo: "+ beyblade.getTipo() +"</td>";
			umBeys += "\t\t\t<td>&nbsp;Descrição: "+ beyblade.getDescricao() +"</td>";
			umBeys += "\t\t\t<td>&nbsp;Preço: "+ beyblade.getPreco() +"</td>";
			umBeys += "\t\t</tr>";
			umBeys += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-PRODUTO>", umBeys);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Beys</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/produto/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/produto/list/" + FORM_ORDERBY_NOME + "\"><b>Nome</b></a></td>\n" +
        		"\t<td><a href=\"/produto/list/" + FORM_ORDERBY_TIPO + "\"><b>Tipo</b></a></td>\n" +
        		"\t<td><a href=\"/produto/list/" + FORM_ORDERBY_DESCRICAO + "\"><b>Descrição</b></a></td>\n" +
        		"\t<td><a href=\"/produto/list/" + FORM_ORDERBY_PRECO + "\"><b>Preço</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Beys> beyblades;
		if (orderBy == FORM_ORDERBY_ID) {                 	beyblades = beyDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_DESCRICAO) {		beyblades = beyDAO.getOrderByDescricao();
		} else if (orderBy == FORM_ORDERBY_PRECO) {			beyblades = beyDAO.getOrderByPreco();
		} else if (orderBy == FORM_ORDERBY_NOME) {          beyblades = beyDAO.getOrderByNome();
		} else if (orderBy == FORM_ORDERBY_TIPO) {          beyblades = beyDAO.getOrderByTipo(); 
		}else {											    beyblades = beyDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Beys p : beyblades) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getID() + "</td>\n" +
            		  "\t<td>" + p.getNome() + "</td>\n" +
            		  "\t<td>" + p.getTipo() + "</td>\n" +
            		  "\t<td>" + p.getDescricao() + "</td>\n" +
            		  "\t<td>" + p.getPreco() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/produto/" + p.getID() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/produto/update/" + p.getID() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteBeys('" + p.getID() + "', '" + p.getNome() + "', '" + p.getPreco() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-PRODUTO>", list);				
	}
	
	
	public int FazerId() 
	{
		List<Beys> beyblades;
		beyblades = beyDAO.get();
		return beyblades.size() + 1;
	}
	
	
	public Object insert(Request request, Response response) {
		String descricao = request.queryParams("descricao");
		float preco = Float.parseFloat(request.queryParams("preco"));
		String nome = request.queryParams("nome");
		String tipo = request.queryParams("tipo");
		
		String resp = "";
		
		Beys beyblade = new Beys(FazerId(), nome, tipo, descricao, preco);
		
		if(beyDAO.insert(beyblade) == true) {
            resp = "Beys (" + nome + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Beys (" + nome + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Beys beyblade = (Beys) beyDAO.get(id);
		
		if (beyblade != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, beyblade, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Beys " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Beys beyblade = (Beys) beyDAO.get(id);
		
		if (beyblade != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, beyblade, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Beys " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
		Beys beyblade = beyDAO.get(id);
        String resp = "";       

        if (beyblade != null) {
        	beyblade.setDescricao(request.queryParams("descricao"));
        	beyblade.setPreco(Float.parseFloat(request.queryParams("preco")));
        	beyblade.setNome(request.queryParams("nome"));
        	beyblade.setTipo(request.queryParams("tipo"));
        	beyDAO.update(beyblade);
        	response.status(200); // success
            resp = "Beys (ID " + beyblade.getID() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Beys (ID \" + beyblade.getID() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Beys beyblade = beyDAO.get(id);
        String resp = "";       

        if (beyblade != null) {
            beyDAO.delete(id);
            response.status(200); // success
            resp = "Beys (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Beys (" + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}
