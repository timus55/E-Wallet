import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { JwtserviceService } from 'src/app/services/jwtservice.service';
import { WalletService } from 'src/app/services/wallet.service';

@Component({
  selector: 'app-general-header',
  templateUrl: './general-header.component.html',
  styleUrls: ['./general-header.component.css']
})
export class GeneralHeaderComponent implements OnInit {
  constructor(private formBuilder:FormBuilder,private router:Router,private service:WalletService) { }

  ngOnInit() {
    
  }
  




}
