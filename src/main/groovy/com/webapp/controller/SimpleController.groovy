package com.webapp.controller

import com.webapp.integration.MqSyncClient
import com.webapp.integration.TaskManager
import com.webapp.model.ConfData
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.RequestMethod

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping

import javax.inject.Inject

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**

 */
@Slf4j
@Controller
@RequestMapping("/conf.html")
class SimpleController {


    @Inject
    TaskManager manager

    @RequestMapping(method = RequestMethod.GET)
    public String showForm(ModelMap model) {
        ConfData data = new ConfData()
        model.addAttribute("confData", data)
        "conf" //view name
    }

    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(@ModelAttribute("intervalData") ConfData data, ModelMap model) {
        log.info("---------------- input:" + groovy.json.JsonOutput.toJson(data))
        model.addAttribute("confData", data)
        //client.queryMainframe(null)

        manager.concurrentRun(data)
        "conf" //view name
    }
}