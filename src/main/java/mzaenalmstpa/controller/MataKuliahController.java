package mzaenalmstpa.controller;

import jakarta.validation.Valid;
import mzaenalmstpa.model.MataKuliahModel;
import mzaenalmstpa.service.MataKuliahService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/matkul")
public class MataKuliahController {
    private MataKuliahService mataKuliahService;

    @Autowired
    public MataKuliahController(MataKuliahService mataKuliahService){
        this.mataKuliahService = mataKuliahService;
    }

    @GetMapping
    public ModelAndView index(){
        ModelAndView view = new ModelAndView("matkul/index.html");
        List<MataKuliahModel> result = mataKuliahService.get();
        view.addObject("dataList", result);
        return view;
    }

    @GetMapping("/add")
    public ModelAndView add() {
        ModelAndView view = new ModelAndView("matkul/add.html");
        view.addObject("matkul", new MataKuliahModel());
        return view;
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid @ModelAttribute("matkul") MataKuliahModel request, BindingResult result) {
        ModelAndView view = new ModelAndView("matkul/add.html");
        if (Boolean.FALSE.equals(mataKuliahService.validCode(request))){
            FieldError fieldError = new FieldError("matkul","code","Code "+ request.getCode() +" already exist");
            result.addError(fieldError);
        }
        if (Boolean.FALSE.equals(mataKuliahService.validName(request))){
            FieldError fieldError = new FieldError("matkul","code","Name with "+request.getName() +" already exist");
            result.addError(fieldError);
        }
        if (result.hasErrors()){
            view.addObject("matkul", request);
            return view;
        }

        this.mataKuliahService.save(request);
        return new ModelAndView("redirect:/matkul");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id) {
        MataKuliahModel matkul = this.mataKuliahService.getById(id);
        if (matkul == null) {
            return new ModelAndView("redirect:/matkul");
        }

        ModelAndView view = new ModelAndView("matkul/edit.html");
        view.addObject("matkul", matkul);
        return view;
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid @ModelAttribute("matkul") MataKuliahModel request, BindingResult result) {
        if (result.hasErrors()){
            ModelAndView view = new ModelAndView("matkul/edit.html");
            view.addObject("matkul", request);
            return view;
        }
        this.mataKuliahService.update(request.getId(), request);
        return new ModelAndView("redirect:/matkul");
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id") String id) {
        MataKuliahModel matkul = this.mataKuliahService.getById(id);
        if (matkul == null) {
            return new ModelAndView("redirect:/matkul");
        }

        ModelAndView view = new ModelAndView("matkul/detail.html");
        view.addObject("data", matkul);
        return view;
    }

    @PostMapping("/delete")
    public ModelAndView delete(@ModelAttribute MataKuliahModel request) {
        MataKuliahModel matkul = mataKuliahService.getById(request.getId());
        if (matkul == null) {
            return new ModelAndView("redirect:/matkul");
        }
        this.mataKuliahService.delete(request.getId());
        return new ModelAndView("redirect:/matkul");
    }
}
