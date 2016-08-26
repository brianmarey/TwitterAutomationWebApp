package com.careydevelopment.twitterautomation.controller;

import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.careydevelopment.twitterautomation.jpa.entity.Project;
import com.careydevelopment.twitterautomation.jpa.entity.Role;
import com.careydevelopment.twitterautomation.jpa.entity.TwitterUser;
import com.careydevelopment.twitterautomation.jpa.repository.ProjectRepository;
import com.careydevelopment.twitterautomation.util.Constants;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(Application.class)
@WebAppConfiguration
//@SpringBootTest
@RunWith(SpringRunner.class)
@WebMvcTest(CreateProjectController.class)
public class CreateProjectControllerTest  {

	@Autowired
	WebApplicationContext wac;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	MockHttpSession session;
		
	@MockBean
	ProjectRepository projectRepository;
	
	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	
	@Test
	public void testCreateProjectNotLoggedIn() {
		TwitterUser owner = ControllerHelper.getBasicUser();				
		List<Project> projects = new ArrayList<Project>();
        given(projectRepository.findByOwner(owner)).willReturn(projects);
        
        try {
        	//we should get a redirect if we're not logged in
	        mockMvc.perform(get("/createProject")
	        		.session(session)
	        		.accept(MediaType.TEXT_PLAIN))
	        .andExpect(status().is(302));
        } catch (Exception e) {
        	e.printStackTrace();
        	Assert.fail();
        }
	}
	
	
	@Test
	public void testCreateProjectWrongRole() {
		TwitterUser owner = ControllerHelper.getNoRolesUser();
				
		List<Project> projects = new ArrayList<Project>();
		
        given(projectRepository.findByOwner(owner)).willReturn(projects);
        
        try {
	        //check with the wrong permission
        	session.setAttribute(Constants.TWITTER_ENTITY, owner);
	        mockMvc.perform(get("/createProject")
	        		.session(session)
	        		.accept(MediaType.TEXT_PLAIN))
	        .andExpect(status().is(302));

	        session.removeAttribute(Constants.TWITTER_ENTITY);
        } catch (Exception e) {
        	e.printStackTrace();
        	Assert.fail();
        }
	}

	
	@Test
	public void testCreateProjectWin() {
		TwitterUser owner = new TwitterUser();
		owner.setScreenName("joebob");
				
		List<Project> projects = new ArrayList<Project>();
		
        
        
        try {
			Role role = new Role();
			role.setRoleName("basic");
			List<Role> roles = new ArrayList<Role>();
			roles.add(role);
			owner.setRoles(roles);
        	session.setAttribute(Constants.TWITTER_ENTITY, owner);
        	
        	given(this.projectRepository.findByOwner(owner)).willReturn(projects);
        	MvcResult result = this.mockMvc.perform(get("/createProject")
	        		.session(session)
	        		.accept(MediaType.TEXT_PLAIN))
	        		.andExpect(status().is(200))
	        		.andExpect(view().name("createaproject"))
	        		.andReturn();
      	
        	String body = result.getResponse().getContentAsString();
        	Assert.assertTrue(body.indexOf(">Create a Project<") > -1);
        } catch (Exception e) {
        	e.printStackTrace();
        	Assert.fail();
        }
	}
}
