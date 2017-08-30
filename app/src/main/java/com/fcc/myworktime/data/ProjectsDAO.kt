package com.fcc.myworktime.data

import com.fcc.myworktime.di.qualifiers.ProjectsDbRef
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

/**
 * Created by firta on 8/31/2017.
 * Class used to manage operation on the projects table in the [FirebaseDatabase]
 */
class ProjectsDAO @Inject constructor(@ProjectsDbRef val projectsDbRef: DatabaseReference){
}