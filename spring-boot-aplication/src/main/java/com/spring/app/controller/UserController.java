package com.spring.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.app.entity.User;
import com.spring.app.repository.RoleRepository;
import com.spring.app.service.UserService;

@Controller
public class UserController {
	
	@Autowired 
	UserService userService;
	
	@Autowired
	RoleRepository roleRepository;
	
	@RequestMapping(value = {"/","/login"} , method = RequestMethod.GET)
	public String index() {
		return "index";
	}
	
	@GetMapping("/userForm")
	public String userForm(Model model) {
		model.addAttribute("userForm", new User()); //objeto que se manda para ser llenado en el formulario
		model.addAttribute("roles",roleRepository.findAll()); //mandar los roles
		model.addAttribute("userList", userService.getAllUsers()); //mandar la lista de los usuarios
		model.addAttribute("listTab","active"); //para seleccionar cual será la pestaña listTab/formTab activa
		return "user-form/user-view";
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("userForm", new User()); //objeto que se manda para ser llenado en el formulario
		model.addAttribute("roles",roleRepository.findAll()); 
		model.addAttribute("formTab","active");
		return "user-form/user-register";
	}
	
	@RequestMapping(value = "/userForm" , method = RequestMethod.POST)
	public String postUserForm(@Valid @ModelAttribute("userForm") User user, BindingResult result, ModelMap model) {
			if(result.hasErrors()) {
				model.addAttribute("userForm", user);
				model.addAttribute("formTab","active");
			}else {
				try {
					userService.createUser(user);
					model.addAttribute("userForm", user);
					model.addAttribute("listTab","active");
				} catch (Exception e) {
					model.addAttribute("formErrorMessage",e.getMessage());
					model.addAttribute("userForm", user);//el objeto con los valores con los que llegó
					model.addAttribute("formTab","active");//para que se ponga la pestaña del form
					model.addAttribute("userList", userService.getAllUsers());//para la lista de la pestaña
					model.addAttribute("roles",roleRepository.findAll());//para el form seleccionar el rol
				}
			}

			model.addAttribute("userList", userService.getAllUsers());
			model.addAttribute("roles",roleRepository.findAll());
			return "user-form/user-view";
		}
	
	@RequestMapping(value = "/register" , method = RequestMethod.POST)
	public String postRegister(@Valid @ModelAttribute("userForm") User user, BindingResult result,
			RedirectAttributes flash, ModelMap model) {
			if(result.hasErrors()) {
				model.addAttribute("userForm", user);
			}else {
				
				BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
		       
		        //El String que mandamos al metodo encode es el password que queremos encriptar.
		        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
				try {
					userService.createUser(user);
				} catch (Exception e) {
					model.addAttribute("formErrorMessage",e.getMessage());
					model.addAttribute("userForm", user);
					model.addAttribute("formTab","active");
					return "user-form/user-register";
				}
			}
			flash.addFlashAttribute("success", "Se ha registrado con exito");
			return "redirect:/login";
		}
	
	@GetMapping("/editUser/{id}")
	public String getEditUserForm(Model model, @PathVariable(name="id") Long id) throws Exception {
		User user = userService.getUserById(id);
		
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("roles",roleRepository.findAll());
		model.addAttribute("userForm", user);
		model.addAttribute("formTab","active");//Activa el tab del formulario.
		
		model.addAttribute("editMode",true);//para que el formulario sepa si es editar usuario o crear usuario
		
		return "user-form/user-view";
	}
	
	@GetMapping("/update/{email}")
	public String update(Model model, @PathVariable(name="email") String email) throws Exception{
		//User user = userService.getUserById(id);
		User user = userService.getUserByEmail(email);
		//model.addAttribute("userForm", user);
		model.addAttribute("userForm", user); //objeto que se manda para ser llenado en el formulario
		model.addAttribute("roles",roleRepository.findAll()); 
		model.addAttribute("formTab","active");
		return "user-form/update";
	}
	
	
	@GetMapping("/palindromo")
	public String palindromo(Model model) {
		model.addAttribute("userForm", new User()); //objeto que se manda para ser llenado en el formulario
		model.addAttribute("roles",roleRepository.findAll()); //mandar los roles
		model.addAttribute("userList", userService.getAllUsers()); //mandar la lista de los usuarios
		model.addAttribute("listTab","active"); //para seleccionar cual será la pestaña listTab/formTab activa
		return "user-form/palindromo";
	}
	
	@PostMapping("/update")
	public String postUpdate(@Valid @ModelAttribute("userForm")User user, ModelMap model,
			RedirectAttributes flash)throws Exception  {

			try {
				userService.updateUserSinId(user);
				model.addAttribute("userForm", user);
				model.addAttribute("listTab","active");
			} catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				model.addAttribute("userForm", user);
				model.addAttribute("formTab","active");
				model.addAttribute("userList", userService.getAllUsers());
				model.addAttribute("roles",roleRepository.findAll());
				model.addAttribute("editMode","true");
			}
		
		
		model.addAttribute("userList", userService.getUserByEmail(user.getEmail()));
		model.addAttribute("roles",roleRepository.findAll());
		flash.addFlashAttribute("success", "Se ha actualizado la información con exito");
		return "redirect:/userForm";
		//return "user-form/user-view";
		
	}
	
	@PostMapping("/editUser")
	public String postEditUserForm(@Valid @ModelAttribute("userForm")User user, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("userForm", user);
			model.addAttribute("formTab","active");
			model.addAttribute("editMode","true");
		}else {
			try {
				userService.updateUser(user);
				model.addAttribute("userForm", new User());
				model.addAttribute("listTab","active");
			} catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				model.addAttribute("userForm", user);
				model.addAttribute("formTab","active");
				model.addAttribute("userList", userService.getAllUsers());
				model.addAttribute("roles",roleRepository.findAll());
				model.addAttribute("editMode","true");
			}
		}
		
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("roles",roleRepository.findAll());
		return "user-form/user-view";
		
	}
	
	@GetMapping("/editUser/cancel")
	public String cancelEditUser(ModelMap model) {
		return "redirect:/userForm";
	}
	
	@GetMapping("/deleteUser/{id}")
	public String deleteUser(Model model, @PathVariable(name="id") Long id) {
		try {
			userService.deleteUser(id);
		} catch (Exception e) {
			model.addAttribute("deleteError",e.getMessage());
		}
		return userForm(model);
	}
	
}
