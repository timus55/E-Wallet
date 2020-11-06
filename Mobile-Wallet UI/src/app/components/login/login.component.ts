import { ViewEncapsulation } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import * as jwt_decode from 'jwt-decode';
import { JwtserviceService } from 'src/app/services/jwtservice.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class LoginComponent implements OnInit {
  loginFormByNumber:FormGroup;

  authRequest: any;
  response: any;
  submitted:boolean=false;
  constructor(private builder: FormBuilder,private router:Router,private service:JwtserviceService) { }
  ngOnInit() {

    if (localStorage.getItem("token") != null) 
    {
     // alert("Session Expired..!! Please login or signup");
      this.router.navigate(['home']);
    }

    this.loginFormByNumber=this.builder.group({  // creating a new instance of the form (group)
      password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(20), Validators.pattern('[a-zA-Z0-9@#!$%^&*]+')]],
      mobile: ['', [Validators.required, Validators.minLength(10),Validators.maxLength(10), Validators.pattern('[6-9]{1}[0-9]+')]]
    });
  }  
  loginByNumber()
  {
    this.submitted = true;
    if (this.loginFormByNumber.invalid)
    {
      return;
    }
    let mobile = this.loginFormByNumber.controls.mobile.value;
    let password = this.loginFormByNumber.controls.password.value;
    this.authRequest = {
      "mobile": mobile,
      "password": password
    };
    let resp = this.service.generateTokenNumber(this.authRequest);
    resp.subscribe(data => {
      console.log(data);
        localStorage.token = data
       // var decoded = jwt_decode(data);
       // var role = decoded['jti'];
        //localStorage.role=role;
        //if (role == 'user') {
          this.router.navigate(["/home"]);
       // }
       // else if(role == 'admin'){
        //  this.router.navigate(["/admin"]);
       // }

  
},
err=>{
  alert("Incorrect details");
  console.log(err);
}
    );
}
}
