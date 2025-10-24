package service;
import java.io.File;
import java.util.List;
import java.util.Scanner;
import dao.CamisaDAO;
import model.Camisa;
import spark.Request;
import spark.Response;
public class CamisaService {
    private CamisaDAO camisaDAO = new CamisaDAO();
    private String form;
    private final int FORM_INSERT = 1;
    private final int FORM_DETAIL = 2;
    private final int FORM_UPDATE = 3;
    private final int FORM_ORDERBY_ID = 1;
    private final int FORM_ORDERBY_MODELO = 2;
    private final int FORM_ORDERBY_MARCA = 3;
    private final int FORM_ORDERBY_PRECO = 4;
    public CamisaService() {
        makeForm();
    }
    public void makeForm() {
        makeForm(FORM_INSERT, new Camisa(), FORM_ORDERBY_MODELO);
    }
    public void makeForm(int orderBy) {
        makeForm(FORM_INSERT, new Camisa(), orderBy);
    }
    public void makeForm(int tipo, Camisa camisa, int orderBy) {
        String nomeArquivo = "form.html";
        form = "";
        try {
            Scanner entrada = new Scanner(new File(nomeArquivo));
            while (entrada.hasNext()) {
                form += (entrada.nextLine() + "\n");
            }
            entrada.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String umProduto = "";
        if (tipo != FORM_INSERT) {
            umProduto += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
            umProduto += "\t\t<tr>";
            umProduto += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/camisa/list/1\">Nova Camisa</a></b></font></td>";
            umProduto += "\t\t</tr>";
            umProduto += "\t</table>";
            umProduto += "\t<br>";
        }

        if (tipo == FORM_INSERT || tipo == FORM_UPDATE) {
            String action = "/camisa/";
            String name, modelo, buttonLabel;
            if (tipo == FORM_INSERT) {
                action += "insert";
                name = "Inserir Nova Camisa";
                modelo = "";
                buttonLabel = "Inserir";
            } else {
                action += "update/" + camisa.getID();
                name = "Atualizar Camisa (ID " + camisa.getID() + ")";
                modelo = camisa.getModelo();
                buttonLabel = "Atualizar";
            }
            umProduto += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
            umProduto += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
            umProduto += "\t\t<tr>";
            umProduto += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
            umProduto += "\t\t</tr>";
            umProduto += "\t\t<tr>";
            umProduto += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
            umProduto += "\t\t</tr>";
            umProduto += "\t\t<tr>";
            umProduto += "\t\t\t<td>&nbsp;Modelo: <input class=\"input--register\" type=\"text\" name=\"modelo\" value=\"" + modelo + "\"></td>";
            umProduto += "\t\t\t<td>Marca: <input class=\"input--register\" type=\"text\" name=\"marca\" value=\"" + camisa.getMarca() + "\"></td>";
            umProduto += "\t\t</tr>";
            umProduto += "\t\t<tr>";
            umProduto += "\t\t\t<td>&nbsp;Tamanho: <input class=\"input--register\" type=\"text\" name=\"tamanho\" value=\"" + camisa.getTamanho() + "\"></td>";
            umProduto += "\t\t\t<td>Preço (R$): <input class=\"input--register\" type=\"number\" step=\"0.01\" name=\"preco\" value=\"" + camisa.getPreco() + "\"></td>";
            umProduto += "\t\t</tr>";
            umProduto += "\t\t<tr>";
            umProduto += "\t\t\t<td colspan=\"2\">&nbsp;</td>";
            umProduto += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\"" + buttonLabel + "\" class=\"input--main__style input--button\"></td>";
            umProduto += "\t\t</tr>";
            umProduto += "\t</table>";
            umProduto += "\t</form>";
        } else if (tipo == FORM_DETAIL) {
            umProduto += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
            umProduto += "\t\t<tr>";
            umProduto += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhes da Camisa (ID " + camisa.getID() + ")</b></font></td>";
            umProduto += "\t\t</tr>";
            umProduto += "\t\t<tr>";
            umProduto += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
            umProduto += "\t\t</tr>";
            umProduto += "\t\t<tr>";
            umProduto += "\t\t\t<td>&nbsp;Modelo: " + camisa.getModelo() + "</td>";
            umProduto += "\t\t\t<td>Marca: " + camisa.getMarca() + "</td>";
            umProduto += "\t\t\t<td>Tamanho: " + camisa.getTamanho() + "</td>";
            umProduto += "\t\t\t<td>Preço: R$ " + camisa.getPreco() + "</td>";
            umProduto += "\t\t</tr>";
            umProduto += "\t\t<tr>";
            umProduto += "\t\t\t<td colspan=\"3\">&nbsp;</td>";
            umProduto += "\t\t</tr>";
            umProduto += "\t</table>";
        } else {
            System.out.println("ERRO! Tipo não identificado " + tipo);
        }
        form = form.replaceFirst("<UM-PRODUTO>", umProduto);

        String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
        list += "\n<tr><td colspan=\"8\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Camisas</b></font></td></tr>\n" +
                "\n<tr><td colspan=\"8\">&nbsp;</td></tr>\n" +
                "\n<tr>\n" +
                "\t<td><a href=\"/camisa/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
                "\t<td><a href=\"/camisa/list/" + FORM_ORDERBY_MODELO + "\"><b>Modelo</b></a></td>\n" +
                "\t<td><a href=\"/camisa/list/" + FORM_ORDERBY_MARCA + "\"><b>Marca</b></a></td>\n" +
                "\t<td><b>Tamanho</b></td>\n" +
                "\t<td><a href=\"/camisa/list/" + FORM_ORDERBY_PRECO + "\"><b>Preço</b></a></td>\n" +
                "\t<td align=\"center\"><b>Detalhar</b></td>\n" +
                "\t<td align=\"center\"><b>Atualizar</b></td>\n" +
                "\t<td align=\"center\"><b>Excluir</b></td>\n" +
                "</tr>\n";

        List<Camisa> camisas;
        if (orderBy == FORM_ORDERBY_ID) { camisas = camisaDAO.getOrderByID();
        } else if (orderBy == FORM_ORDERBY_MODELO) { camisas = camisaDAO.getOrderByModelo();
        } else if (orderBy == FORM_ORDERBY_MARCA) { camisas = camisaDAO.getOrderByMarca();
        } else if (orderBy == FORM_ORDERBY_PRECO) { camisas = camisaDAO.getOrderByPreco();
        } else { camisas = camisaDAO.get();
        }
        int i = 0;
        String bgcolor = "";
        for (Camisa p : camisas) {
            bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
            list += "\n<tr bgcolor=\"" + bgcolor + "\">\n" +
                    "\t<td>" + p.getID() + "</td>\n" +
                    "\t<td>" + p.getModelo() + "</td>\n" +
                    "\t<td>" + p.getMarca() + "</td>\n" +
                    "\t<td>" + p.getTamanho() + "</td>\n" +
                    "\t<td>R$ " + p.getPreco() + "</td>\n" +
                    "\t<td align=\"center\" valign=\"middle\"><a href=\"/camisa/" + p.getID() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
                    "\t<td align=\"center\" valign=\"middle\"><a href=\"/camisa/update/" + p.getID() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
                    "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteCamisa('" + p.getID() + "', '" + p.getModelo() + "', '" + p.getMarca() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
                    "</tr>\n";
        }
        list += "</table>";
        form = form.replaceFirst("<LISTAR-PRODUTO>", list);
    }
    public Object insert(Request request, Response response) {
        String modelo = request.queryParams("modelo");
        String marca = request.queryParams("marca");
        String tamanho = request.queryParams("tamanho");
        double preco = Double.parseDouble(request.queryParams("preco"));
        String resp = "";
        Camisa camisa = new Camisa(-1, modelo, marca, tamanho, preco);
        if (camisaDAO.insert(camisa)) {
            resp = "Camisa (" + modelo + ") inserida!";
            response.status(201);
        } else {
            resp = "Camisa (" + modelo + ") não inserida!";
            response.status(404);
        }
        makeForm();
        return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    }
    public Object get(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Camisa camisa = camisaDAO.get(id);
        if (camisa != null) {
            response.status(200);
            makeForm(FORM_DETAIL, camisa, FORM_ORDERBY_MODELO);
        } else {
            response.status(404);
            String resp = "Camisa " + id + " não encontrada.";
            makeForm();
            form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
        }
        return form;
    }
    public Object getToUpdate(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Camisa camisa = camisaDAO.get(id);
        if (camisa != null) {
            response.status(200);
            makeForm(FORM_UPDATE, camisa, FORM_ORDERBY_MODELO);
        } else {
            response.status(404);
            String resp = "Camisa " + id + " não encontrada.";
            makeForm();
            form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
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
        Camisa camisa = camisaDAO.get(id);
        String resp = "";
        if (camisa != null) {
            camisa.setModelo(request.queryParams("modelo"));
            camisa.setMarca(request.queryParams("marca"));
            camisa.setTamanho(request.queryParams("tamanho"));
            camisa.setPreco(Double.parseDouble(request.queryParams("preco")));
            camisaDAO.update(camisa);
            response.status(200);
            resp = "Camisa (ID " + camisa.getID() + ") atualizada!";
        } else {
            response.status(404);
            resp = "Camisa (ID " + id + ") não encontrada!";
        }
        makeForm();
        return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    }
    public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Camisa camisa = camisaDAO.get(id);
        String resp = "";
        if (camisa != null) {
            camisaDAO.delete(id);
            response.status(200);
            resp = "Camisa (" + id + ") excluída!";
        } else {
            response.status(404);
            resp = "Camisa (" + id + ") não encontrada!";
        }
        makeForm();
        return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    }
}