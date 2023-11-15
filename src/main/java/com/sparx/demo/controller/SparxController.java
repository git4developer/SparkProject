package com.sparx.demo.controller;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparx.demo.SparxService;

@RestController
public class SparxController {
	
	@Autowired
	private SparxService ss;

	@GetMapping("/write")
	public ResponseEntity<String> WriteCsv() throws EncryptedDocumentException, IOException{
		          return this.ss.readWrite();
	}
	
	
}
