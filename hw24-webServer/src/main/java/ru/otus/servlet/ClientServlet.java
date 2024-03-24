package ru.otus.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import ru.otus.hibernate.crm.model.Address;
import ru.otus.hibernate.crm.model.Client;
import ru.otus.hibernate.crm.model.Phone;
import ru.otus.hibernate.crm.service.DBServiceClient;
import ru.otus.services.TemplateProcessor;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class ClientServlet extends HttpServlet {

    private static final String CLIENTS_PAGE_TEMPLATE = "clients.html";

    private final transient DBServiceClient dbServiceClient;

    private final transient TemplateProcessor templateProcessor;

    public ClientServlet(DBServiceClient dbServiceClient, TemplateProcessor templateProcessor) {
        this.dbServiceClient = dbServiceClient;
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();

        var clients =
                dbServiceClient.findAll().stream().map(PojoClient::new).toList();
        paramsMap.put("clients", clients);

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(CLIENTS_PAGE_TEMPLATE, paramsMap));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String phones = request.getParameter("phones");

        Client newClient = new Client(name);

        if (StringUtils.isNoneBlank(address)) {
            newClient.setAddress(new Address(null, address));
        }

        if (StringUtils.isNoneBlank(phones)) {
            var phoneList = Arrays.stream(phones.split(","))
                    .map(ph -> new Phone(null, ph))
                    .toList();
            newClient.getPhones().addAll(phoneList);
        }

        dbServiceClient.saveClient(newClient);

        response.sendRedirect("/clients");
    }
}