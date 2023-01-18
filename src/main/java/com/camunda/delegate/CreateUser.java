package com.camunda.delegate;

import com.camunda.repo.UserRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class CreateUser implements JavaDelegate {
    @Autowired
    public UserRepository userRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        URL url = new URL("http://192.168.68.116:8081/user");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        String jsonInputString = "{\"firstName\": \"" + delegateExecution.getVariable("firstName") + "\", \"lastName\": \"" + delegateExecution.getVariable("lastName") + "\", \"age\": \"" + delegateExecution.getVariable("age") + "\"}";
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        ObjectValue typedUserValue = Variables.objectValue(content).serializationDataFormat("application/json").create();
        delegateExecution.setVariable("User", typedUserValue);
    }
}