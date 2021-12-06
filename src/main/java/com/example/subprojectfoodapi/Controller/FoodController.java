package com.example.subprojectfoodapi.Controller;

import com.example.subprojectfoodapi.Entity.Food;
import com.example.subprojectfoodapi.Repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author MPHuy on 26/11/2021
 */
@RestController
@RequestMapping("/api/food")
public class FoodController {

//    private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));

    @Autowired
    private FoodRepository foodRepository;

    // get food by id
    @GetMapping("/{id}")
    public Food getFood(@PathVariable int id){
        return foodRepository.findById(id).get();
    }

//    @GetMapping
//    public List<Food> allFoods(){
//        return foodRepository.findAll();
//    }

    // get list food trending
    @GetMapping("/t")
    public List<Food> listTrendingFood(@RequestParam(defaultValue = "0") int page
            , @RequestParam(defaultValue = "0") int size){

        Pageable pageable;
        if(page < 0 || size <= 0)
            pageable = Pageable.unpaged();
        else
            pageable = PageRequest.of(page, size);

        return foodRepository.findAll(pageable).getContent();
    }

    // get all food trending
    @GetMapping("/trengding")
    public List<Food> trendingFood(){
        return listTrendingFood(listTrendingFood(0, 0).size() - 10,
                listTrendingFood(0, 0).size());
    }

    // get list food favourites
    @GetMapping("/f")
    public List<Food> listFavouritesFood(@RequestParam(defaultValue = "0") int page
            , @RequestParam(defaultValue = "0") int size){
        Pageable pageable;

        if(page < 0 || size <= 0)
            pageable = Pageable.unpaged();
        else
            pageable = PageRequest.of(page, size);

        return foodRepository.findAllByPrice(pageable);
    }

    // get all food favourites
    @GetMapping("/favourites")
    public List<Food> favouritesFood(){
        return listFavouritesFood(listFavouritesFood(0, 0).size() - 10,
                listFavouritesFood(0, 0).size());
    }

    // save food
    @PostMapping
    public String saveFood(@RequestBody Food food){
        foodRepository.save(food);
        return "save success: "+ LocalDateTime.now();
    }

    // delete food
    @DeleteMapping("/{id}")
    public void deleteFood(@PathVariable int id){
        foodRepository.deleteById(id);
    }

    // update food
    @PutMapping("{id}")
    public Food updateFood(@RequestBody Food food, @PathVariable int id){
        food.setId(id);
        return foodRepository.save(food);
    }
}
