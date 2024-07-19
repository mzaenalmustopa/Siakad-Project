package mzaenalmstpa.controller;

import jakarta.validation.Valid;
import mzaenalmstpa.entity.LookupEntity;
import mzaenalmstpa.model.JurusanModel;
import mzaenalmstpa.model.MahasiswaModel;
import mzaenalmstpa.service.JurusanService;
import mzaenalmstpa.service.LookupService;
import mzaenalmstpa.service.MahasiswaService;
import mzaenalmstpa.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/mahasiswa")
public class MahasiswaController {
    private MahasiswaService mahasiswaService;
    private JurusanService jurusanService;
    private LookupService lookupService;

    @Autowired
    public MahasiswaController(MahasiswaService mahasiswaService, JurusanService jurusanService, LookupService lookupService){
        this.mahasiswaService = mahasiswaService;
        this.jurusanService = jurusanService;
        this.lookupService = lookupService;
    }
    @GetMapping
    public ModelAndView index(){
        ModelAndView view = new ModelAndView("mahasiswa/index.html");
        List<MahasiswaModel> result = mahasiswaService.get();
        view.addObject("dataList", result);
        return view;
    }

    @GetMapping("/add")
    public ModelAndView add(){
        ModelAndView view = new ModelAndView("mahasiswa/add.html");
        //List<JurusanModel> result = jurusanService.get();
        //view.addObject("jurusanList", result);
        view.addObject("genderList", lookupService.getByGroup(Constants.GENDER));
        view.addObject("agamaList", lookupService.getByGroup(Constants.AGAMA));
        view.addObject("jurusanList", jurusanService.get());
        //untuk order
        view.addObject("byPosition", Comparator.comparing(LookupEntity::getPosition));

        view.addObject("mahasiswa", new MahasiswaModel());
        return view;
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid @ModelAttribute("mahasiswa") MahasiswaModel request, BindingResult result){
        ModelAndView view = new ModelAndView("mahasiswa/add.html");
        if (Boolean.FALSE.equals(mahasiswaService.validNim(request))){
            FieldError fieldError = new FieldError("mahasiswa","nim","invalid nim");
            result.addError(fieldError);
        }
        if (Boolean.FALSE.equals(mahasiswaService.validName(request))){
            ObjectError objectError = new ObjectError("invalidName","Name "+ request.getName() +"Not valid");
            result.addError(objectError);
        }
        if (result.hasErrors()){
            List<JurusanModel> data = jurusanService.get();
            view.addObject("jurusanList", data);
            view.addObject("genderList", lookupService.getByGroup(Constants.GENDER));
            view.addObject("agamaList", lookupService.getByGroup(Constants.AGAMA));
            view.addObject("jurusanList", jurusanService.get());
            //untuk order
            view.addObject("byPosition", Comparator.comparing(LookupEntity::getPosition));
            view.addObject("mahasiswa", request);
            return view;
        }

        this.mahasiswaService.save(request);
        return new ModelAndView("redirect:/mahasiswa");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id){
        MahasiswaModel mahasiswa = this.mahasiswaService.getById(id);
        if (mahasiswa == null){
            return new ModelAndView("redirect:/mahasiswa");
        }
        List<JurusanModel> jurusan = jurusanService.get();

        ModelAndView view = new ModelAndView("mahasiswa/edit.html");
        view.addObject("jurusan", jurusan);
        view.addObject("genderList", lookupService.getByGroup(Constants.GENDER));
        view.addObject("agamaList", lookupService.getByGroup(Constants.AGAMA));
        view.addObject("jurusanList", jurusanService.get());
        //untuk order
        view.addObject("byPosition", Comparator.comparing(LookupEntity::getPosition));
        view.addObject("mahasiswa", mahasiswa);
        return view;

    }

    @PostMapping("/update")
    public ModelAndView update(@Valid @ModelAttribute("mahasiswa") MahasiswaModel request, BindingResult result){
        if (result.hasErrors()){
            ModelAndView view = new ModelAndView("mahasiswa/edit.html");
            List<JurusanModel> data = jurusanService.get();
            view.addObject("jurusanList", data);
            view.addObject("genderList", lookupService.getByGroup(Constants.GENDER));
            view.addObject("agamaList", lookupService.getByGroup(Constants.AGAMA));
            view.addObject("jurusanList", jurusanService.get());
            //untuk order
            view.addObject("byPosition", Comparator.comparing(LookupEntity::getPosition));
            view.addObject("mahasiswa", request);
            return view;
        }
        this.mahasiswaService.update(request.getId(), request);
        return new ModelAndView("redirect:/mahasiswa");
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id") String id){
        MahasiswaModel result = this.mahasiswaService.getById(id);
        if (result == null){
            return new ModelAndView("redirect:/mahasiswa");
        }

        ModelAndView view = new ModelAndView("mahasiswa/detail.html");
        view.addObject("data", result);
        return view;
    }

    @PostMapping("/delete")
    public ModelAndView delete(@ModelAttribute MahasiswaModel request) {
        MahasiswaModel mahasiswa = mahasiswaService.getById(request.getId());
        if (mahasiswa == null) {
            return new ModelAndView("redirect:/mahasiswa");
        }

        this.mahasiswaService.delete(request.getId());
        return new ModelAndView("redirect:/mahasiswa");
    }
}
