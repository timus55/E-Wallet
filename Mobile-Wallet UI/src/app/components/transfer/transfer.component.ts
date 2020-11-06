import { ViewEncapsulation } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { WalletService } from 'src/app/services/wallet.service';

@Component({
  selector: 'app-transfer',
  templateUrl: './transfer.component.html',
  styleUrls: ['./transfer.component.css'],
  encapsulation: ViewEncapsulation.None

})
export class TransferComponent implements OnInit {
  transferFunds: FormGroup;
  message: any;
  transferToBank: FormGroup;
  pinForm :FormGroup;
  view: boolean = true;

  constructor(private builder: FormBuilder, private router: Router, private service: WalletService) { }

  ngOnInit() {
   
    if (localStorage.getItem("token") === null) 
    {
      alert("Session Expired..!! Please login or signup");
      this.router.navigate(['loginByNumber']);
    }
    this.transferFunds = this.builder.group({
      mobile: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(10), Validators.pattern('[6-9]{1}[0-9]+')]],
      amount: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(5), Validators.pattern('[0-9]+')]],
      pin: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(4), Validators.pattern('[0-9]+')]]


    });
    this.transferToBank = this.builder.group({
      cardnumber: ['', [Validators.required, Validators.minLength(16), Validators.maxLength(16), Validators.pattern('[0-9]+')]],
      amount: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(5), Validators.pattern('[0-9]+')]],
      pin: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(4), Validators.pattern('[0-9]+')]]

    });

    this.pinForm = this.builder.group({
      password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(20), Validators.pattern('[a-zA-Z0-9@#!$%^&*]+')]]
    })
  }


  transferFriend() {

    this.service.transfer(localStorage.token, this.transferFunds.controls['mobile'].value,
      this.transferFunds.controls['amount'].value,
      this.transferFunds.controls['pin'].value).subscribe(data => {
        this.message = data;
        console.log(this.message);
        alert(data)

        this.router.navigate(['/transaction']);
      }, err => {

        console.log(err)
        // this.router.navigate(["/home"])
      });

  }

  transferBank() {
    this.service.withdraw(localStorage.token, this.transferToBank.controls['cardnumber'].value,
      this.transferToBank.controls['amount'].value,
      this.transferToBank.controls['pin'].value).subscribe(data => {
        this.message = data;
        console.log(this.message);
        alert(data)
        this.router.navigate(['/transaction']);
      }, err => {
        console.log(err)
        // this.router.navigate(["/home"])
      });

  }

  getPin() {
    this.service.forgotPin(localStorage.token,this.pinForm.controls['password'].value).subscribe(data=>{
      console.log(data);
      alert(data);
      this.router.navigate(['/transfer']);
    })
  }

  toggleViewList() {
    this.view = false;
    console.log(this.view)
  }
  toggleViewCard() {
    this.view = true;
    console.log(this.view)
  }

}
