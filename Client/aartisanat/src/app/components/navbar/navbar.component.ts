import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UsersService } from 'src/app/services/user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent   {
user: any;
  

  constructor(
    private userService: UsersService, 
    private http: HttpClient,
    private router: Router
    ){

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
  sellerDashboard()
  {
    this.router.navigateByUrl('/sellerDashborad');
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
}