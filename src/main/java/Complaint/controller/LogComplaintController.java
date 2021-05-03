package Complaint.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/")
public class LogComplaintController {

    // We need an endpoint to log complaint
    // another endpoint to get complaint transaction
    // another endpoint to get customer details
    // endpoint to check if transactions have been looged
    //

    // create a new complaint
    @PreAuthorize("hasAuthority()")
    @RequestMapping(value = "log-complaints")
    public @ResponseBody
    List<Complaint> logComplaint(@RequestParam("complaints") String complaintStr,
                                 @RequestParam("filesListArr[]") MultipartFile[] files,
                                 @RequestParam("filesNames[]") String[] fileGroupNames,
                                 @RequestParam("cbn_category") String cbnCategoryString,
                                 Principal principal)
}
