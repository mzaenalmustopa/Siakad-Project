package mzaenalmstpa.controller;

import jakarta.validation.Valid;
import mzaenalmstpa.entity.LookupEntity;
import mzaenalmstpa.model.DosenModel;
import mzaenalmstpa.service.DosenService;
import mzaenalmstpa.service.LookupService;
import mzaenalmstpa.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.Comparator;
import java.util.List;


@Controller
@RequestMapping("/dosen")
public class DosenController {
    private DosenService dosenService;
    private LookupService lookupService;



    @Autowired
    public DosenController(DosenService dosenService, LookupService lookupService){
        this.dosenService = dosenService;
        this.lookupService = lookupService;
    }

    @GetMapping
    public ModelAndView index(){
        ModelAndView view = new ModelAndView("dosen/index.html");
        List<DosenModel> result = dosenService.get();
        view.addObject("dataList", result);
        return view;
    }

    @GetMapping("/add")
    public ModelAndView add() {
        ModelAndView view = new ModelAndView("dosen/add.html");
        view.addObject("genderList", lookupService.getByGroup(Constants.GENDER));
        view.addObject("byPosition", Comparator.comparing(LookupEntity::getPosition));
        view.addObject("dosen", new DosenModel());
        return view;

    }

    @PostMapping("/save")
    public ModelAndView save(@Valid @ModelAttribute("dosen") DosenModel request, BindingResult result) {
        ModelAndView view = new ModelAndView("dosen/add.html");
        if (Boolean.FALSE.equals(dosenService.validNip(request))){
            FieldError fieldError = new FieldError("dosen","code","Code "+request.getNip() +" already exust");
            result.addError(fieldError);
        }
        if (Boolean.FALSE.equals(dosenService.validName(request))){
            FieldError fieldError = new FieldError("dosen", "code","Name with "+ request.getName() +" already exist");
            result.addError(fieldError);
        }
        if (result.hasErrors()){
            view.addObject("genderList", lookupService.getByGroup(Constants.GENDER));
            view.addObject("byPosition", Comparator.comparing(LookupEntity::getPosition));
            view.addObject("dosen", request);
            return view;
        }
        this.dosenService.save(request);
        return new ModelAndView("redirect:/dosen");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id) {
        DosenModel dosen = this.dosenService.getById(id);
        if (dosen == null) {
            return new ModelAndView("redirect:/dosen");
        }

        ModelAndView view = new ModelAndView("dosen/edit.html");
        view.addObject("genderList", lookupService.getByGroup(Constants.GENDER));
        view.addObject("byPosition", Comparator.comparing(LookupEntity::getPosition));
        view.addObject("dosen", dosen);
        return view;
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid @ModelAttribute("dosen") DosenModel request, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView view = new ModelAndView("dosen/edit.html");
            view.addObject("genderList", lookupService.getByGroup(Constants.GENDER));
            view.addObject("byPosition", Comparator.comparing(LookupEntity::getPosition));
            view.addObject("dosen", request);
            return view;
        }
        this.dosenService.update(request.getId(), request);
        return new ModelAndView("redirect:/dosen");
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id") String id) {
        DosenModel dosen = this.dosenService.getById(id);
        if (dosen == null) {
            return new ModelAndView("redirect:/dosen");
        }

        ModelAndView view = new ModelAndView("dosen/detail.html");
        view.addObject("data", dosen);
        return view;
    }

    @PostMapping("/delete")
    public ModelAndView delete(@ModelAttribute DosenModel request) {
        DosenModel dosen = dosenService.getById(request.getId());
        if (dosen == null) {
            return new ModelAndView("redirect:/dosen");
        }
        this.dosenService.delete(request.getId());
        return new ModelAndView("redirect:/dosen");
    }
}
