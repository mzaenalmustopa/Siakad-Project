package mzaenalmstpa.controller;

import jakarta.validation.Valid;
import mzaenalmstpa.entity.LookupEntity;
import mzaenalmstpa.model.DosenModel;
import mzaenalmstpa.model.KelasModel;
import mzaenalmstpa.model.MataKuliahModel;
import mzaenalmstpa.model.RuangModel;
import mzaenalmstpa.service.*;
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
@RequestMapping("/kelas")
public class KelasController {
    private KelasService service;
    private RuangService ruangService;
    private MataKuliahService mataKuliahService;
    private DosenService dosenService;
    private LookupService lookupService;

    @Autowired
    public KelasController(KelasService service, RuangService ruangService, MataKuliahService mataKuliahService, DosenService dosenService, LookupService lookupService) {
        this.service = service;
        this.ruangService = ruangService;
        this.mataKuliahService = mataKuliahService;
        this.dosenService = dosenService;
        this.lookupService = lookupService;
    }

    @GetMapping
    public ModelAndView index(){
        ModelAndView view = new ModelAndView("kelas/index.html");
        List<KelasModel> result = service.get();
        view.addObject("dataList", result);
        return view;
    }

    @GetMapping("/add")
    public ModelAndView add(){
        ModelAndView view = new ModelAndView("kelas/add.html");

        view.addObject("hariList", lookupService.getByGroup("HARI"));
        view.addObject("semesterList", lookupService.getByGroup("SEMESTER"));
        view.addObject("onlineList", lookupService.getByGroup("ONLINE"));
        view.addObject("byPosition", Comparator.comparing(LookupEntity::getPosition));
        List<RuangModel> result = ruangService.get();
        view.addObject("ruangList", result);
         view.addObject("kelas", new KelasModel());
        List<MataKuliahModel> result1 = mataKuliahService.get();
        view.addObject("matkulList", result1);
        view.addObject("kelas", new KelasModel());
        List<DosenModel> result2 = dosenService.get();
        view.addObject("dosenList", result2);
        view.addObject("kelas", new KelasModel());
        return view;
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid @ModelAttribute("kelas") KelasModel request, BindingResult result){
        ModelAndView view = new ModelAndView("kelas/add.html");
        if (Boolean.FALSE.equals(service.validKode(request))){
            FieldError fieldError = new FieldError("kelas","kode","invalid code");
            result.addError(fieldError);
        }
        if (Boolean.FALSE.equals(service.validNamaHari(request))){
            ObjectError objectError = new ObjectError("invalidNamaHari", "Name "+ request.getNamaHari() +"Not valid");
            result.addError(objectError);
        }
        if (result.hasErrors()){
            view.addObject("hariList", lookupService.getByGroup("HARI"));
            view.addObject("semesterList", lookupService.getByGroup("SEMESTER"));
            view.addObject("onlineList", lookupService.getByGroup("ONLINE"));
            view.addObject("byPosition", Comparator.comparing(LookupEntity::getPosition));
            List<RuangModel> data = ruangService.get();
            view.addObject("ruangList", data);
            view.addObject("kelas", request);
            List<MataKuliahModel> data1 = mataKuliahService.get();
            view.addObject("matkulList", data1);
            view.addObject("kelas", request);
            List<DosenModel> data2 = dosenService.get();
            view.addObject("dosenList", data2);
            view.addObject("kelas", request);
            return view;
        }
        this.service.save(request);
        return new ModelAndView("redirect:/kelas");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id){
        KelasModel kelasModel = this.service.getById(id);
        if (kelasModel == null){
            return new ModelAndView("redirect:/kelas");
        }
        List<RuangModel> ruang = ruangService.get();
        List<MataKuliahModel> matakuliah = mataKuliahService.get();
        List<DosenModel> dosen = dosenService.get();

        ModelAndView view = new ModelAndView("kelas/edit.html");
        view.addObject("hariList", lookupService.getByGroup("HARI"));
        view.addObject("semesterList", lookupService.getByGroup("SEMESTER"));
        view.addObject("onlineList", lookupService.getByGroup("ONLINE"));
        view.addObject("byPosition", Comparator.comparing(LookupEntity::getPosition));
        view.addObject("kelas", kelasModel);
        view.addObject("ruangList", ruang);
        view.addObject("matkulList", matakuliah);
        view.addObject("dosenList", dosen);
        return view;
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid @ModelAttribute("kelas") KelasModel request, BindingResult result){
        if (result.hasErrors()){
            List<RuangModel> ruang = ruangService.get();
            List<MataKuliahModel> matakuliah = mataKuliahService.get();
            List<DosenModel> dosen = dosenService.get();

            ModelAndView view = new ModelAndView("kelas/edit.html");
            view.addObject("hariList", lookupService.getByGroup("HARI"));
            view.addObject("semesterList", lookupService.getByGroup("SEMESTER"));
            view.addObject("onlineList", lookupService.getByGroup("ONLINE"));
            view.addObject("byPosition", Comparator.comparing(LookupEntity::getPosition));
            view.addObject("kelas", request);
            view.addObject("ruangList", ruang);
            view.addObject("matkulList", matakuliah);
            view.addObject("dosenList", dosen);
            return view;
        }
        this.service.update(request.getId(), request);
        return new ModelAndView("redirect:/kelas");
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id") String id) {
        KelasModel kelasModel = this.service.getById(id);
        if (kelasModel == null){
            return new ModelAndView("redirect:/kelas");
        }

        ModelAndView view = new ModelAndView("kelas/detail.html");
        view.addObject("data", kelasModel);
        return view;
    }

    @PostMapping("/delete")
    public ModelAndView delete(@ModelAttribute KelasModel request){
        KelasModel kelasModel = service.getById(request.getId());
        if (kelasModel == null){
            return new ModelAndView("redirect:/kelas");
        }
        this.service.delete(request.getId());
        return new ModelAndView("redirect:/kelas");

    }
}
