import { Component } from '@angular/core';
import { UsersService } from '../../services/user.service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { User } from '../../models/user.model';

@Component({
  selector: 'app-login-admin',
  templateUrl: './login-admin.component.html',
  styleUrls: ['./login-admin.component.scss']
})
export class LoginAdminComponent {
  user: User = new User();
  constructor(
    private userService: UsersService, 
    private http: HttpClient,
    private router: Router
    ){

    }

  ngOnInit(): void {

  }

  login() {
      this.userService.loginUser(this.user).subscribe({
        next: (response) => {
          this.user = response;
          // if(response.role.id==2)
          console.log(this.user);
          sessionStorage.setItem('user_id', String(response.id));
          this.router.navigateByUrl("/dashboardadmin");
        },
        error: (error) => {
          console.error('login failed:', error);
        }
      });
   
  }
}
