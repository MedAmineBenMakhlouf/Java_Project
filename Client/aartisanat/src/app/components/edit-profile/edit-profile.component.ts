import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UsersService } from 'src/app/services/user.service';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.scss']
})
export class EditProfileComponent {
  user :any;
  infouser:any={};
  selectedFile: File | null = null;
  constructor(
    private userService: UsersService,
    private router: Router
  ) {}

  ngOnInit() {
    
    this.userService.getLoggedUser().subscribe({
      next: (data) => {
        this.user = data
        console.log(JSON.stringify(data.user.username));
        
        console.log(this.user.user);
        
      },
      error: (error) => console.error('There was an error!', error)
    });
  }

  onFileSelected(event: Event): void {
    const element = event.currentTarget as HTMLInputElement;
    let fileList: FileList | null = element.files;
    if (fileList) {
      this.selectedFile = fileList[0];
    }
  }

  editPicture() {
    if (this.selectedFile) {
      const userId = sessionStorage.getItem('user_id');
      if (userId) {
        const formData = new FormData();
        formData.append('file', this.selectedFile, this.selectedFile.name); // Changed 'picture' to 'file'
  
        this.userService.changepictureprofile(+userId, formData).subscribe({
          next: (response) => {
            // Handle response, update user data or redirect
            
          },
          error: (error) => console.error('Error uploading image:', error)
        });
      } else {
        console.error('User ID is null');
      }
    } else {
      console.error('No file selected');
    }
    window.location.reload();
  }

  editInfo()
  {
    const userId = sessionStorage.getItem('user_id');
    // const formData = new FormData();
    console.log("---------------------------------",JSON.stringify(this.user.username));
    
    this.infouser={
      username:this.user.user.username,
      phone: this.user.user.phone,
      address:this.user.user.address,
      email:this.user.user.email
    }
    // formData.append('user', new Blob([JSON.stringify(this.user)], { type: 'application/json' }))
    console.log(this.infouser);
    this.userService.editInfo(userId,this.infouser).subscribe({
      next: (response) => {
        this.router.navigateByUrl('/sellerDashborad');
        
      },
      error: (error) => console.error('error updating info', error)
    });
  }

  dashboardUser() {
    this.router.navigateByUrl('/dashboard');
  }
  dashboardSeller() {
    this.router.navigateByUrl('/sellerDashborad');
  }
  logout() {
    this.userService.logoutUser();
    sessionStorage.removeItem("user_id");
    this.router.navigateByUrl('/register');
  }

  goToBuyerHistory()
  {
    this.router.navigateByUrl("/user/edit/buyer");
  }
  goToSellerHistory()
  {
    this.router.navigateByUrl("/user/edit/seller");
  }
}
