package be.tftic.spring.demo.api;

import be.tftic.spring.demo.api.model.CredentialsForm;
import be.tftic.spring.demo.bll.TaskService;
import be.tftic.spring.demo.bll.TaskServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class DemoController {


    private final List<String> names = List.of(
            "luc",
            "pol",
            "marie"
    );

    public DemoController() {
        System.out.println(" -- SPRING EST EN TRAIN D'INSTANCIER DemoController -- ");
    }

    @GetMapping( "/hello" )
    public String hello(){
        return "hello world!";
    }

    // GET - http://localhost:8080/addition?membreA=_&membreB=_
    @GetMapping("/addition")
    public int addition(@RequestParam("membreA") int a, @RequestParam int membreB){
        return a + membreB;
    }

    // POST - http://localhost:8080/login
    @PostMapping("/login")
    public boolean login(@RequestBody CredentialsForm credentials){
        return Objects.equals( credentials.getUsername(), "user" ) &&
                Objects.equals( credentials.getPassword(), "pass" );
    }

    // GET - http://localhost:8080/name/1
    @GetMapping("/name/{index}")
    public String getName(@PathVariable("index") int i){
        return names.get(i);
    }

}
