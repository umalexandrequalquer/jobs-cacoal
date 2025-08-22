package umalexandre.empregos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import umalexandre.empregos.dtos.HomeDTO;
import umalexandre.empregos.service.CompanyService;


@RestController()
@RequestMapping("/jobs/")
public class HomeController {


    @Autowired
    private CompanyService companyService;

    @GetMapping("home/")
    public ResponseEntity<Page<HomeDTO>>home(
            @RequestParam(required = false) String query,
            @PageableDefault(page = 0, size = 1)
            @SortDefault.SortDefaults({@SortDefault(sort = {}, direction = Sort.Direction.DESC)
            }) Pageable pageable
    ) {
        return ResponseEntity.ok(companyService.homeDTO(query, pageable));
    }



}

