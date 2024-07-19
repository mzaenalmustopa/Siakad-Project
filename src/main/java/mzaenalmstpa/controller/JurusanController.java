package mzaenalmstpa.controller;

import jakarta.validation.Valid;
import mzaenalmstpa.model.FakultasModel;
import mzaenalmstpa.model.JurusanModel;
import mzaenalmstpa.service.FakultasService;
import mzaenalmstpa.service.JurusanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/jurusan")
public class JurusanController {
    private JurusanService service;
    private FakultasService fakultasService;

    @Autowired
    public JurusanController(JurusanService service, FakultasService fakultasService){
        this.service = service;
        this.fakultasService = fakultasService;
    }

    @GetMapping
    public ModelAndView index(){
        ModelAndView view = new ModelAndView("jurusan/index.html");
        List<JurusanModel> result = service.get();
        view.addObject("dataList", result);
        return view;
    }

    @GetMapping("/add")
    public ModelAndView add(){
        ModelAndView view = new ModelAndView("jurusan/add.html");
        List<FakultasModel> result = fakultasService.get();
        view.addObject("fakultasList", result);
        view.addObject("jurusan", new JurusanModel());
        return view;
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid @ModelAttribute("jurusan") JurusanModel request, BindingResult result){
        ModelAndView view = new ModelAndView("jurusan/add.html");
        if (Boolean.FALSE.equals(service.validCode(request))){
            FieldError fieldError = new FieldError("jurusan","code","invalid code");
            result.addError(fieldError);
        }
        if (Boolean.FALSE.equals(service.validName(request))){
            ObjectError objectError = new ObjectError("invalidName", "Name "+ request.getName() +"Not valid");
            result.addError(objectError);
        }
        if (result.hasErrors()){
            List<FakultasModel> data = fakultasService.get();
            view.addObject("fakultasList", data);
            view.addObject("jurusan", request);
            return view;
        }

        this.service.save(request);
        return new ModelAndView("redirect:/jurusan");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id) {
        JurusanModel jurusan = this.service.getById(id);
        if (jurusan == null) {
            return new ModelAndView("redirect:/jurusan");
        }
        List<FakultasModel> fakultas = fakultasService.get();

        ModelAndView view = new ModelAndView("jurusan/edit.html");
        view.addObject("jurusan", jurusan);
        view.addObject("fakultasList", fakultas);
        return view;
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid @ModelAttribute("jurusan") JurusanModel request, BindingResult result){
        if (result.hasErrors()){
            ModelAndView view = new ModelAndView("jurusan/edit.html");
            view.addObject("jurusan", request);
            return view;
        }
        this.service.update(request.getId(), request);
        return new ModelAndView("redirect:/jurusan");
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id") String id){
        JurusanModel fakultas = this.service.getById(id);
        if (fakultas == null){
            return new ModelAndView("redirect:/jurusan");
        }

        ModelAndView view = new ModelAndView("jurusan/detail.html");
        view.addObject("data", fakultas);
        return view;
    }

    @PostMapping("/delete")
    public ModelAndView delete(@ModelAttribute JurusanModel request){
        JurusanModel jurusan = service.getById(request.getId());
        if (jurusan == null){
            return new ModelAndView("redirect:/jurusan");
        }
        this.service.delete(request.getId());
        return new ModelAndView("redirect:/jurusan");
    }
}
