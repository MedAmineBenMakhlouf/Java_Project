import { Component, OnInit } from '@angular/core';
import { User } from '../../models/user.model'; // Adjust the path as necessary
import { UsersService } from '../../services/user.service'; // Adjust the path as necessary
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-registration',
  templateUrl: './login-registration.component.html',
  styleUrls: ['./login-registration.component.scss']
})
export class LoginRegisterComponent implements OnInit {
  user: User = new User();

  isSignedUp = false;

 

  selectedFile: File | null = null;
  isSignUp =false;
  constructor(
    private userService: UsersService, 
    private http: HttpClient,
    private router: Router
    ){

    }

    onFileSelected(event: any): void {
      if (event.target.files.length > 0) {
        this.selectedFile = event.target.files[0];
      }
    }
    toggleSignUp(): void {
      this.isSignUp = !this.isSignUp;
    }
    ngOnInit(): void {
     
    }
  registerUser() {
    if (this.selectedFile) {
      this.userService.registerUser(this.user, this.selectedFile).subscribe({
        next: (user) => {
          sessionStorage.setItem('user_id', String(user.id));
          this.router.navigateByUrl("/dashboard");
        },
        error: (error) => {
          console.error('Registration failed:', error);
        }
      });
    } else {
      console.error('File not selected.');
    }
   
  }
  login() {
    
    this.userService.loginUser(this.user).subscribe({
      next: (response) => {
        sessionStorage.setItem('user_id', String(response.id));
        this.router.navigateByUrl("/dashboard");
      },
      error: (error) => {
        console.error('login failed:', error);
      }
    });
 
}
  
}