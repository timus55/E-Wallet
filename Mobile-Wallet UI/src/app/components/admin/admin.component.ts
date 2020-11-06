import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';
import { WalletService } from 'src/app/services/wallet.service';
import { userDetailsForAdmin } from 'src/app/models/userDetailsForAdmin';
import { UserInfo } from 'src/app/models/user';


@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})

export class AdminComponent implements OnInit {
  users :UserInfo[]=[]

// cards: card[];
  
  constructor(private router: Router,private walletservice:WalletService) { }

  //  ngOnInit(): void {
  //    console.log("In OnInit Block")
  //    this.Userlist.getUserList().subscribe(data=>{
  //    this.Users=data;
  //      console.log(this.Users);
  //    },err=>{
  //      alert("Session Expired....Login Again");
  //      this.router.navigate(["/login"])
  //    });
  //  }

  ngOnInit(){
    if (localStorage.getItem("token") === null) 
    {
      alert("Session Expired..!! Please login or signup");
      this.router.navigate(['loginByNumber']);
    }


this.walletservice.getAll(localStorage.token).subscribe(data=>{
  this.users = data;
  console.log(this.users)
})
    


  }

  disableUser(mobile:number)
  {
    this.walletservice.disableUser(localStorage.token,mobile)
    .subscribe(
      data=>{
        console.log(data);
        alert("User Deleted Successfully");
      
        this.walletservice.getAll(localStorage.token).subscribe(data=>{
          this.users = data;
          console.log(this.users)
        })
        this.router.navigate(['admin']);
      }
    )
  }
}
