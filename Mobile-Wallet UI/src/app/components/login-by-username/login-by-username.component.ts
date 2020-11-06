import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import * as jwt_decode from 'jwt-decode';
import { JwtserviceService } from 'src/app/services/jwtservice.service';

@Component({
  selector: 'app-login-by-username',
  templateUrl: './login-by-username.component.html',
  styleUrls: ['./login-by-username.component.css']
})
export class LoginByUsernameComponent implements OnInit {
  loginFormByUsername: FormGroup;
  authRequest: any;
  response: any;
  submitted:boolean=false;
  constructor(private formBuilder: FormBuilder,private router:Router,private service:JwtserviceService) { }

  ngOnInit() {
    if (localStorage.getItem("token") != null) 
    {
     // alert("Session Expired..!! Please login or signup");
      this.router.navigate(['home']);
    }

this.loginFormByUsername=this.formBuilder.group({
    password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(20), Validators.pattern('[a-zA-Z0-9@#!$%^&*]+')]],
    username: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(30), Validators.pattern('[a-zA-Z0-9.-_]{1,}@[a-zA-Z.-]{2,}[.]{1}[a-zA-Z]{2,}')]],
  });

}
loginByUsername()
  {
    this.submitted = true;
    if (this.loginFormByUsername.invalid)
    {
      return;
    }
    let username = this.loginFormByUsername.controls.username.value;
    let password = this.loginFormByUsername.controls.password.value;
    this.authRequest = {
      "username": username,
      "password": password
    };
    console.log(username);
    console.log(password);
    let resp = this.service.generateTokenUsername(this.authRequest);
    resp.subscribe(data => {
      console.log(data);
      // if (data == "null") {
      //   alert("Invalid username/password");
      // } else {
        localStorage.token = data
        var decoded = jwt_decode(data);
        var role = decoded['jti'];
        localStorage.role=role;
        if (role == 'user') {
          this.router.navigate(["/home"]);
        }
        else if(role == 'admin'){
          this.router.navigate(["/admin"]);
        }

  
},
err=>{
  alert("Incorrect details");
  console.log(err);
}
    );
}
}
