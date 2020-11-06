import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { JwtserviceService } from 'src/app/services/jwtservice.service';

@Component({
  selector: 'app-login-by-aadhar',
  templateUrl: './login-by-aadhar.component.html',
  styleUrls: ['./login-by-aadhar.component.css']
})
export class LoginByAadharComponent implements OnInit {
loginFormByAadhar:FormGroup;
submitted:boolean=false;
authRequest: any;
  response: any;
constructor(private formBuilder: FormBuilder,private router:Router,private service:JwtserviceService) { }

  ngOnInit() {
    if (localStorage.getItem("token") != null) 
    {
     // alert("Session Expired..!! Please login or signup");
      this.router.navigate(['home']);
    }

    this.loginFormByAadhar = this.formBuilder.group({
     
      password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(20), Validators.pattern('[a-zA-Z0-9@#!$%^&*]+')]],
      aadhar: ['', [Validators.required, Validators.minLength(12), Validators.maxLength(12), Validators.pattern('[1-9]{1}[0-9]+')]]
    });
  }


  loginByAadhar()
  {
    this.submitted = true;
    if (this.loginFormByAadhar.invalid)
    {
      return;
    }
    let aadhar = this.loginFormByAadhar.controls.aadhar.value;
    let password = this.loginFormByAadhar.controls.password.value;
    this.authRequest = {
      "aadhar": aadhar,
      "password": password
    };
    console.log(aadhar);
    console.log(password);
    let resp = this.service.generateTokenAadhar(this.authRequest);
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
