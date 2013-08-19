package com.webapp.controller

import com.webapp.integration.MqSyncClient
import com.webapp.integration.TaskManager
import com.webapp.model.ConfData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.RequestMethod

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping

import javax.inject.Inject

/**

 */
@Controller
@RequestMapping("/conf.html")
class SimpleController {

    @Inject
    MqSyncClient client


    @RequestMapping(method = RequestMethod.GET)
    public String showForm(ModelMap model) {
        ConfData data = new ConfData()
        model.addAttribute("confData", data)
        "conf" //view name
    }

    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(@ModelAttribute("intervalData") ConfData data, ModelMap model) {
        println("---------->    " + groovy.json.JsonOutput.toJson(data))
        model.addAttribute("confData", data)
        client.queryMainframe(null)

        /*
        def mgr = new TaskManager()
        mgr.concurrentRun(data.interval, data.concurrenceSize)
        */
        "conf" //view name
    }
}