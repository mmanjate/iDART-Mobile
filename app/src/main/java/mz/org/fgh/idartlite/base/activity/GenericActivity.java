package mz.org.fgh.idartlite.base.activity;

import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;

public interface GenericActivity {

    /**
     * Initialize a new {@link BaseViewModel}
     *
     * @return an instance os {@link BaseViewModel}
     */
    BaseViewModel initViewModel();
}
