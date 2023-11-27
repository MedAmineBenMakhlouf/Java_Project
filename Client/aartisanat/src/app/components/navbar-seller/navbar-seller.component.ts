import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user.model';
import { UsersService } from 'src/app/services/user.service';

@Component({
  selector: 'app-navbar-seller',
  templateUrl: './navbar-seller.component.html',
  styleUrls: ['./navbar-seller.component.scss']
})
export class NavbarSellerComponent {
  user: any;
  constructor(
    private userService: UsersService,
    private http: HttpClient,
    private router: Router
  ) {

  }
  ngOnInit() {
    this.userService.getLoggedUser().subscribe({
      next: (data) => {

        this.user = data;
        console.log(sessionStorage.getItem('user_id'));

      },
      error: (error) => {
        console.error('There was an error!', error);
      }
    });
  }
  dashboard() {
    this.router.navigateByUrl('/dashboard');
  }
  logout() {
    this.userService.logoutUser();
    sessionStorage.removeItem("user_id");
    this.router.navigateByUrl('/login');

  }
  settingPage()
  {
    this.router.navigateByUrl('editprofile');
  }
  addProduct()
  {
    this.router.navigateByUrl('addProduct');
  }
  myList()
  {
    this.router.navigateByUrl('myList');
  }
  Config_Wallet()
  {
    this.router.navigateByUrl('configWallet');
  }
}
