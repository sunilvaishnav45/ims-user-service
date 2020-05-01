package userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import userservice.dao.*;
import userservice.dto.AttributeValuesResponse;
import userservice.entity.AttributeValues;
import userservice.entity.Attributes;
import userservice.entity.Category;
import userservice.entity.User;
import userservice.service.ProductService;
import userservice.service.UserService;
import userservice.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/at")
public class AttributeController {

    @Autowired
    private UserService userService;

    @Autowired
    private AttributeDao attributeDao;

    @Autowired
    private AttributeValuesDao attributeValuesDao;

    @Autowired
    private ProductService productService;

    @PostMapping(value = "/attribute", consumes = "application/json")
    public ResponseEntity<Attributes> saveAttribute(@RequestBody Attributes attributes){
        User loggedInUser = userService.getLoggedInUser();
        if(!userService.userHasWritePermission(loggedInUser))
            return new ResponseEntity("User doesn't have write permission", HttpStatus.FORBIDDEN);
        if(productService.attributeExists(attributes.getAttribute()))
            return new ResponseEntity("Attribute already exists",HttpStatus.BAD_REQUEST);
        attributeDao.save(attributes);
        return new ResponseEntity(attributes,HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/attribute")
    public ResponseEntity<List<Category>> getAttribute(HttpServletRequest request, HttpServletRequest response, @RequestParam(required = false) String attributeIds){
        User loggedInUser = userService.getLoggedInUser();
        List<Attributes> attributesList = null;
        if(!userService.userHasReadPermission(loggedInUser))
            return new ResponseEntity("User doesn't has read permission",HttpStatus.FORBIDDEN);
        if(attributeIds!=null){
            if(!StringUtils.convertCommaSepratedIntoList(attributeIds).isPresent())
                return new ResponseEntity("Attribute ids are not valid",HttpStatus.BAD_REQUEST);
            attributesList = (List<Attributes>) attributeDao.findAllById(StringUtils.convertCommaSepratedIntoList(attributeIds).get());
        }
        else
            attributesList = (List<Attributes>) attributeDao.findAll();
        return new ResponseEntity(attributesList,HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/attribute-values", consumes = "application/json")
    public ResponseEntity<AttributeValuesResponse> saveAttribute(@RequestBody List<AttributeValues> attributeValuesList){
        User loggedInUser = userService.getLoggedInUser();
        if(!userService.userHasWritePermission(loggedInUser))
            return new ResponseEntity("User doesn't have write permission",HttpStatus.FORBIDDEN);
        AttributeValuesResponse attributeValuesResponse = productService.saveAttributeValue(attributeValuesList);
        return new ResponseEntity(attributeValuesResponse,HttpStatus.ACCEPTED);
    }
}
