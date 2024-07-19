package mzaenalmstpa.controller;

import mzaenalmstpa.model.KelasDetailModel;
import mzaenalmstpa.model.KelasModel;
import mzaenalmstpa.model.MahasiswaModel;
import mzaenalmstpa.service.KelasDetailService;
import mzaenalmstpa.service.KelasService;
import mzaenalmstpa.service.MahasiswaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/kelasdetail")
public class KelasDetailController {
    private KelasDetailService service;
    private KelasService kelasService;
    private MahasiswaService mahasiswaService;

    @Autowired
    public KelasDetailController(KelasDetailService service, KelasService kelasService, MahasiswaService mahasiswaService) {
        this.service = service;
        this.kelasService = kelasService;
        this.mahasiswaService = mahasiswaService;
    }

    @GetMapping
    public ModelAndView index(){
        ModelAndView view = new ModelAndView("kelasdetail/index.html");
        List<KelasDetailModel> result = service.get();
        view.addObject("dataList", result);
        return view;
    }

    @GetMapping("/add")
    public ModelAndView add(){
        ModelAndView view = new ModelAndView("kelasdetail/add.html");
        List<KelasModel> result = kelasService.get();
        List<MahasiswaModel> result1 = mahasiswaService.get();
        view.addObject("kelasList", result);
        view.addObject("mahasiswaList", result1);
        return view;
    }

    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute KelasDetailModel request){
        this.service.save(request);
        return new ModelAndView("redirect:/kelasdetail");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id){
        KelasDetailModel kelasDetailModel = this.service.getById(id);
        if (kelasDetailModel == null){
            return new ModelAndView("redirect:/kelasdetail");
        }
        List<KelasModel> kelas = kelasService.get();
        List<MahasiswaModel> mahasiswa = mahasiswaService.get();

        ModelAndView view = new ModelAndView("kelasdetail/edit.html");
        view.addObject("data", kelasDetailModel);
        view.addObject("kelasList", kelas);
        view.addObject("mahasiswaList", mahasiswa);
        return view;
    }

    @PostMapping("/update")
    public ModelAndView update(@ModelAttribute KelasDetailModel request){
        this.service.update(request.getId(), request);
        return new ModelAndView("redirect:/kelasdetail");
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id") String id){
        KelasDetailModel kelasDetailModel = this.service.getById(id);
        if (kelasDetailModel == null){
            return new ModelAndView("redirect:/kelasdetail");
        }

        ModelAndView view = new ModelAndView("kelas/detail.html");
        view.addObject("data", kelasDetailModel);
        return view;

    }


}
