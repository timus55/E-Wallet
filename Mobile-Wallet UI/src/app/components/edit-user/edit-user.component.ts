import { Component, OnInit } from '@angular/core';
import { Validators } from '@angular/forms';
import { FormBuilder } from '@angular/forms';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { WalletService } from 'src/app/services/wallet.service';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {
  editDetails: FormGroup;
  submitted:boolean=false;
  securityQuestion=[
    "Your favourite Language? ",
     "your favourite superhero of any movie or any comic? ",
   "Your hobbie? ",
     "Your best friend? ",
     "Your pet name? "
   ]
  constructor(private formBuilder: FormBuilder,private router:Router, private service : WalletService) { }

  ngOnInit() {
    if (localStorage.getItem("token") === null) 
    {
      alert("Session Expired..!! Please login or signup");
      this.router.navigate(['loginByNumber']);
    }
    
    this.editDetails = this.formBuilder.group({
      firstName: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(20), Validators.pattern('[a-zA-Z]+')]],
      lastName: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(20), Validators.pattern('[a-zA-Z]+')]],  
      username: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(30), Validators.pattern('[a-zA-Z0-9.-_]{1,}@[a-zA-Z.-]{2,}[.]{1}[a-zA-Z]{2,}')]],
      securityQ:['',Validators.required],
      securityA:['',Validators.required],
     
    });
    this.service.getuserdetails(localStorage.token).subscribe(data=>{
      if(data!=null)
      this.editDetails.setValue(data);

    },err=>{
      console.log(err)
      if(err.error=="Session Expired"){
        localStorage.removeItem("token");

        alert("Session Expired....Login Again");
      this.router.navigate(["/loginByNumber"])
      }
      
    });

  }

  editUser(){
    this.submitted=true
    if (this.editDetails.invalid) {
      return;
    }
    this.service.editUser(this.editDetails.getRawValue()).subscribe(data=>{
      alert(data);
      this.router.navigate(['/home']);
      console.log(data);
    },err=>{
      console.log(err)
      if(err.error=="Session Expired"){
        localStorage.removeItem("token");
    
        alert("Session Expired....Login Again");
      this.router.navigate(["/login"])
      }
      
    });
  }
}
