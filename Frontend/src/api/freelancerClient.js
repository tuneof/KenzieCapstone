import BaseClass from "../util/baseClass";
import axios from 'axios'

export default class ExampleClient extends BaseClass {

    constructor(props = {}){
        super();
        const methodsToBind = ['clientLoaded', 'getFreelancerById', 'getAllFreelancers', 'createFreelancer', 'updateFreelancer'];
        this.bindClassMethods(methodsToBind, this);
        this.props = props;
        this.clientLoaded(axios);
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     * @param client The client that has been successfully loaded.
     */
    clientLoaded(client) {
        this.client = client;
        if (this.props.hasOwnProperty("onReady")){
            this.props.onReady();
        }
    }

    /**
     * Gets the freelancer for the given ID.
     * @param id Unique identifier for a concert
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The concert
     */
    async getFreelancerById(id, errorCallback) {
        try {
            const response = await this.client.get(`/freelancer/${id}`);
            return response.data;
        } catch (error) {
            this.handleError("getFreelancerById", error, errorCallback)
        }
    }

    async getAllFreelancers(errorCallback) {
        try {
            const response = await this.client.get(`/freelancer`);
            return response.data;
        } catch (error) {
            this.handleError("getAllFreelancers", error, errorCallback)
        }
    }

    async createFreelancer(name, expertise, rate, location, contact, errorCallback) {
        try {
            const response = await this.client.post(`/freelancer`, {
                "name": name,
                "expertise": expertise,
                "rate": rate,
                "location": location
                "contact": contact
            });
            return response.data;
        } catch (error) {
            this.handleError("createFreelancer", error, errorCallback);
        }
    }

    async updateFreelancer(id, errorCallback) {
        try {
            const response = await this.client.put(`/freelancer/${id}`);
            return response.data;
        } catch (error) {
            this.handleError("getFreelancerById", error, errorCallback)
        }
    }

    async deleteFreelancerById(id, errorCallback) {
        try {
            const response = await this.client.delete(`/freelancer/${id}`);
            return response.data;
        } catch (error) {
            this.handleError("deleteFreelancerById", error, errorCallback)
        }
    }

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(method, error, errorCallback) {
        console.error(method + " failed - " + error);
        if (error.response.data.message !== undefined) {
            console.error(error.response.data.message);
        }
        if (errorCallback) {
            errorCallback(method + " failed - " + error);
        }
    }
}
