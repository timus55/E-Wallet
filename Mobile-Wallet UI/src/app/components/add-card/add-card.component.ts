import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { WalletService } from 'src/app/services/Wallet.service';

@Component({
  selector: 'app-add-card',
  templateUrl: './add-card.component.html',
  styleUrls: ['./add-card.component.css']
})
export class AddCardComponent implements OnInit {
  addcard: FormGroup;
  submitted: boolean = false;

  constructor(private formBuilder: FormBuilder, private router: Router, private walletService: WalletService) { }

  ngOnInit() {
    if (localStorage.getItem("token") === null) {
      alert("Session Expired..!! Please login or signup");
      this.router.navigate(['loginByNumber']);
    }
    this.addcard = this.formBuilder.group({
      cardNumber: ['', [Validators.required, Validators.pattern('[0-9]{16}')]],
      holderName: ['', [Validators.required, Validators.pattern('[a-zA-Z]{3,25}')]],
      expMonth: ['', [Validators.required, Validators.pattern('^(0?[1-9]|1[012])$')]],
      expYear: ['', [Validators.required, Validators.pattern('[2]{1}[0]{1}[2-9]{1}[0-9]{1}')]],
      cvv: ['', [Validators.required, Validators.pattern('[0-9]{3}')]],


    });
  }

  addCard() {
    this.submitted = true;

    if (this.addcard.invalid) {
      return;
    }

    console.log(this.addcard.value);
    this.walletService.addCard(this.addcard.value, localStorage.token).subscribe(data => {
      console.log(data);
      if(data=="Card No. Already Exists")
      {
        alert("Card No. Already Exists");
        this.router.navigate(['add-card']);
      }
      else
      {
      alert(`you've added card sucessfully`);
      this.router.navigate(['view-cards']);
      }
    },
      err => {

        console.log(err.stack);

      })
  }

  addMoney() {
    this.submitted = true;

    if (this.addcard.invalid) {
      return;
    }

    console.log(this.addcard.value);

    alert(this.addcard.value.cardNumber);
    localStorage.card = this.addcard.value.cardNumber;
    localStorage.cvv = this.addcard.value.cvv;
    this.router.navigate(['enter-amount']);


  }

}
