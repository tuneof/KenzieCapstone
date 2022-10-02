import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import freelancerClient from "../api/freelancerClient";

class deleteFreelancer extends baseClass {

    constructor() {
        super();
        this.bindClassMethods(['onDelete', 'renderFreelancer'], this);
        this.dataStore = new DataStore();
    }

    async mount() {
        document.getElementById('delete-freelancer-button').addEventListener('click', this.onDelete);
        this.client = new freelancerClient();

        this.dataStore.addChangeListener(this.renderFreelancer)
    }

// Render Methods --------------------------------------------------------------------------------------------------

    async renderFreelancer() {
        let resultArea = document.getElementById("freelancer-list");

        const freelancers = this.dataStore.get("groups");

        let htmlContent = "<ul>";

        if (freelancers) {
            for(let freelancer of freelancers){
                htmlContent += `<li>
                    Name: ${freelancer.name}<br>
                    Expertise: ${freelancer.expertise}<br>
                    Rate: ${freelancer.rate}<br>
                    Location: ${freelancer.location}<br>
                    Contact: ${freelancer.contact}
                    </li>
                `
            }
            htmlContent += "</ul>";
            resultArea.innerHTML = htmlContent;

        } else {
            resultArea.innerHTML = "No Item";
        }
    }

// Event Handlers --------------------------------------------------------------------------------------------------

    async onDelete(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let id = document.querySelector("freelancer-id").value
        //this.dataStore.set("groups", null);
        let result = await this.client.deleteFreelancerById(id, this.errorHandler);
        this.dataStore.set("freelancer", result);
        if (result) {
            this.showMessage(`Deleted freelancer ${id}`)
        } else {
            this.errorHandler("Error doing delete!  Try again...");
        }

    }

/**
 * Main method to run when the page contents have loaded.
 */
    const main = async () => {
        const groupPage = new GroupPage();
        groupPage.mount();
    };

    window.addEventListener('DOMContentLoaded', main);
}