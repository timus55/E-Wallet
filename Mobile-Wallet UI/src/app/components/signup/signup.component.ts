import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { WalletService } from 'src/app/services/wallet.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  signup: FormGroup;
  submitted:boolean=false;

  securityQuestion=[
    "Your favourite Language? ",
     "your favourite superhero of any movie or any comic? ",
   "Your hobbie? ",
     "Your best friend? ",
     "Your pet name? "
   ]
    constructor(private formBuilder: FormBuilder,private router:Router,private service:WalletService) { }

  ngOnInit() {
    if (localStorage.getItem("token") != null) 
    {
     // alert("Session Expired..!! Please login or signup");
      this.router.navigate(['home']);
    }
    this.signup = this.formBuilder.group({
      firstName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(20), Validators.pattern('[a-zA-Z]+')]],
      lastName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(20), Validators.pattern('[a-zA-Z]+')]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(20), Validators.pattern('[a-zA-Z0-9@#!$%^&*]+')]],
      username: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(30), Validators.pattern('[a-zA-Z0-9.-_]{1,}@[a-zA-Z.-]{2,}[.]{1}[a-zA-Z]{2,}')]],
      mobile: ['', [Validators.required, Validators.minLength(10),Validators.maxLength(10), Validators.pattern('[6-9]{1}[0-9]+')]],
      aadhar: ['', [Validators.required, Validators.minLength(12), Validators.maxLength(12), Validators.pattern('[1-9]{1}[0-9]+')]],
      balance: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(5), Validators.pattern('[0-9]+')]],
      securityQ:['',Validators.required],
      securityA:['',Validators.required],
      role:['',Validators.required]
    });
  }
  addUser()
{
  this.submitted=true;
    if(this.signup.invalid)
    {
    return;
    }
    // console.log(this.signup.value)
    this.service.registerNewUser(this.signup.value).subscribe(data => {
      alert(data); 
      if(data.toString().includes("Pin"))
      {
               
      this.router.navigate(['loginByNumber']);
      }
     
    }, err => {
      console.log(err.error);

    })
}

}
